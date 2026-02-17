package com.example.course.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course.core.data.repository.MainRepository
import com.example.course.core.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    val userCourse: StateFlow<List<Course>> = repository.getAllCoursesFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _userState = MutableStateFlow(UserUiState())
    val userState = _userState.asStateFlow()

    fun setFavorite(course: Course) {
        viewModelScope.launch {
            repository.setFavorite(course.id)
        }
    }
}

data class UserUiState(
    val userBlockList: List<UserBlockItem> = UserBlockItem.mockData
)

enum class UserAction {
    OPEN_PROFILE,
    OPEN_SETTINGS,
    LOGOUT
}

data class UserBlockItem(
    val id: Int,
    val text: String,
    val action: UserAction = UserAction.LOGOUT
) {
    companion object {
        val mockData = listOf(
            UserBlockItem(1, "Написать в поддержку"),
            UserBlockItem(2, "Настройки"),
            UserBlockItem(3, "Выйти из аккаунта"),
        )
    }
}