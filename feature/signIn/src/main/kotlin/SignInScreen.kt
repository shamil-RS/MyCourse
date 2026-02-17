package com.example.course.feature.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.course.core.designsystem.component.CustomOutlinedTextField
import com.example.course.core.designsystem.component.CustomTextButton
import com.example.course.core.designsystem.theme.Blue
import com.example.course.core.designsystem.theme.Green
import com.example.course.core.designsystem.theme.Orange
import com.example.course.core.designsystem.util.AccountBox
import com.example.course.core.designsystem.util.ClearTrailingButton
import com.example.course.core.designsystem.util.LaIcons
import com.example.course.core.designsystem.util.ToggleTextVisibilityTrailingButton
import com.example.course.core.designsystem.util.copy
import com.example.course.core.designsystem.util.safeStringResource
import com.example.course.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val layoutDirection = LocalLayoutDirection.current
    val uriHandler = LocalUriHandler.current

    val vkUrl = stringResource(id = R.string.url_vk)
    val okUrl = stringResource(id = R.string.url_ok)

    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { channel ->
            when (channel) {
                SignInChannel.EmailNotFound -> snackbarHostState.showSnackbar(
                    message = context.getString(R.string.email_not_associated_with_account),
                    withDismissAction = true
                )

                SignInChannel.IncorrectPassword -> snackbarHostState.showSnackbar(
                    message = context.getString(R.string.incorrect_password),
                    withDismissAction = true
                )

                SignInChannel.SignInSuccessfully -> onNavigateToHome()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Black,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.Center,
        ) {
            SignInItem(
                email = screenState.email,
                password = screenState.password,
                contentPadding = innerPadding.copy(
                    layoutDirection = layoutDirection,
                    start = 16.dp,
                    top = 0.dp,
                    end = 16.dp,
                    bottom = 0.dp,
                ),
                emailErrorResId = screenState.emailErrorResId,
                passwordErrorResId = screenState.passwordErrorResId,
                isPasswordVisible = screenState.isPasswordVisible,
                focusManager = focusManager,
                onEmailChangeClick = { viewModel.changeEmail(value = it) },
                onPasswordChangeClick = { viewModel.changePassword(value = it) },
                onTogglePasswordVisibilityClick = viewModel::togglePasswordVisibility,
                onSignInClick = {
                    focusManager.clearFocus()
                    viewModel.signIn()
                },
                onNavigateToSignUp = onNavigateToSignUp,
                onVkUriClick = { uriHandler.openUri(vkUrl) },
                onOkUriClick = { uriHandler.openUri(okUrl) },
            )
        }
    }
}

@Composable
fun SignInItem(
    email: String,
    password: String,
    contentPadding: PaddingValues,
    emailErrorResId: Int?,
    passwordErrorResId: Int?,
    isPasswordVisible: Boolean,
    focusManager: FocusManager,
    onEmailChangeClick: (String) -> Unit,
    onPasswordChangeClick: (String) -> Unit,
    onTogglePasswordVisibilityClick: () -> Unit,
    onSignInClick: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onVkUriClick: () -> Unit,
    onOkUriClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(stringResource(R.string.sign_in), color = Color.White, fontSize = 22.sp)

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Text(stringResource(R.string.email), color = Color.White)

        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { onEmailChangeClick(it) },
            placeholderString = stringResource(id = R.string.email_placeholder),
            errorMessage = safeStringResource(id = emailErrorResId),
            leadingIcon = {
                Icon(imageVector = LaIcons.Email, contentDescription = null)
            },
            trailingIcon = if (email.isEmpty()) null
            else {
                { ClearTrailingButton(onClick = { onEmailChangeClick("") }) }
            },
            enableWhiteSpace = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
            )
        )

        Text(stringResource(R.string.password), color = Color.White)

        CustomOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = { onPasswordChangeClick(it) },
            placeholderString =
                if (isPasswordVisible) stringResource(id = R.string.password_placeholder_show)
                else stringResource(id = R.string.password_placeholder_hide),
            errorMessage = safeStringResource(id = passwordErrorResId),
            leadingIcon = {
                Icon(imageVector = LaIcons.Password, contentDescription = null)
            },
            trailingIcon = {
                ToggleTextVisibilityTrailingButton(
                    onClick = onTogglePasswordVisibilityClick,
                    isVisible = isPasswordVisible
                )
            },
            enableWhiteSpace = false,
            hideText = !isPasswordVisible,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onSignInClick() }
            )
        )

        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green),
            content = {
                Text(
                    text = stringResource(R.string.sign_in),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.dont_have_account), color = Color.White)

            CustomTextButton(
                text = stringResource(id = R.string.sign_up),
                color = Green,
                onClick = onNavigateToSignUp
            )
        }

        CustomTextButton(
            text = stringResource(id = R.string.forgot_password),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Green,
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF4D555E)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            AccountBox(
                icon = R.drawable.vector,
                color = Blue,
                onClick = onVkUriClick
            )
            AccountBox(
                icon = R.drawable.group_11,
                color = Orange,
                onClick = onOkUriClick
            )
        }
    }
}