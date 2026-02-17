package com.example.course.feature.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course.core.data.repository.UsersRepository
import com.example.course.core.domain.InsertUserUseCase
import com.example.course.core.domain.IsEmailAvailableUseCase
import com.example.course.core.domain.SetCurrentUserIdUseCase
import com.example.course.core.domain.ValidateEmailFieldUseCase
import com.example.course.core.domain.ValidateSimpleFieldUseCase
import com.example.course.core.model.User
import com.example.course.core.type.InputError
import com.example.course.core.type.InputResult
import com.example.course.designsystem.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SignUpChannel {
    data object UnavailableEmail : SignUpChannel
    data object SignUpFailed : SignUpChannel
    data object SignUpSuccessfully : SignUpChannel
}

data class SignUpScreenState(
    val email: String,
    val emailErrorResId: Int?,
    val password: String,
    val repeatPassword: String,
    val repeatPasswordErrorResId: Int?,
    val passwordErrorResId: Int?,
    val isPasswordVisible: Boolean,
    val profilePicture: String?,
    val birthDate: String? = "",
    val keepLogged: Boolean,
)

private data class SignUpViewModelState(
    val email: String = "",
    val emailErrorResId: Int? = null,
    val password: String = "",
    val repeatPassword: String = "",
    val repeatPasswordErrorResId: Int? = null,
    val passwordErrorResId: Int? = null,
    val isPasswordVisible: Boolean = false,
    val profilePicture: String? = "",
    val birthDate: String? = "",
    val keepLogged: Boolean = true,
) {
    fun asScreenState() = SignUpScreenState(
        email = email,
        emailErrorResId = emailErrorResId,
        password = password,
        repeatPassword = repeatPassword,
        repeatPasswordErrorResId = repeatPasswordErrorResId,
        passwordErrorResId = passwordErrorResId,
        isPasswordVisible = isPasswordVisible,
        profilePicture = profilePicture,
        birthDate = birthDate,
        keepLogged = keepLogged
    )
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateSimpleFieldUseCase: ValidateSimpleFieldUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val isEmailAvailableUseCase: IsEmailAvailableUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val setCurrentUserIdUseCase: SetCurrentUserIdUseCase,
    private val usersRepository: UsersRepository,
) : ViewModel() {
    private val viewModelState = MutableStateFlow(value = SignUpViewModelState())

    val screenState = viewModelState.map { it.asScreenState() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = viewModelState.value.asScreenState()
    )

    private val _channel = Channel<SignUpChannel>()
    val channel = _channel.receiveAsFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        loadUserData()
    }

    fun changeEmail(value: String) {
        viewModelState.update { it.copy(email = value) }
    }

    fun changePassword(value: String) {
        viewModelState.update { it.copy(password = value) }
    }

    fun changeRepeatPassword(value: String) {
        viewModelState.update { it.copy(repeatPassword = value) }
    }

    fun togglePasswordVisibility() {
        viewModelState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val currentUser = usersRepository.getCurrentUser()
            _user.value = currentUser
        }
    }

    fun signUp() {
        val emailResult = validateEmailFieldUseCase(viewModelState.value.email)
        val passwordResult = validateSimpleFieldUseCase(viewModelState.value.password, minChar = 4)

        val repeatResult =
            validateSimpleFieldUseCase(viewModelState.value.repeatPassword, minChar = 4)

        val passwordsMatch = viewModelState.value.password == viewModelState.value.repeatPassword

        val finalRepeatResult =
            if (repeatResult is InputResult.Success && !passwordsMatch) InputResult.Error(InputError.PasswordsDoNotMatch)
            else repeatResult

        viewModelState.update { state ->
            state.copy(
                emailErrorResId = when (emailResult) {
                    InputResult.Success -> null
                    is InputResult.Error -> when (emailResult.inputError) {
                        InputError.FieldEmpty -> R.string.email_empty_error
                        InputError.FieldInvalid -> R.string.email_invalid_error
                        else -> null
                    }
                },
                passwordErrorResId = when (passwordResult) {
                    InputResult.Success -> null
                    is InputResult.Error -> when (passwordResult.inputError) {
                        InputError.FieldEmpty -> R.string.password_empty_error
                        InputError.FieldLessMinCharacters -> R.string.password_too_short_error
                        else -> null
                    }
                }
            )
        }

        listOf(
            emailResult,
            passwordResult,
            finalRepeatResult
        ).any { inputResult ->
            inputResult is InputResult.Error
        }.also { hasError ->
            if (hasError) return
        }

        viewModelScope.launch {
            val isEmailAvailable = isEmailAvailableUseCase(email = viewModelState.value.email)
            if (!isEmailAvailable) {
                _channel.send(element = SignUpChannel.UnavailableEmail)
                return@launch
            }

            val user = User(
                email = viewModelState.value.email,
                password = viewModelState.value.password,
            )

            val userId = insertUserUseCase(user = user)
            if (userId > 0) {
                setCurrentUserIdUseCase(userId = userId, stayConnected = true)
                _channel.send(element = SignUpChannel.SignUpSuccessfully)
            } else {
                _channel.send(element = SignUpChannel.SignUpFailed)
            }
        }
    }
}