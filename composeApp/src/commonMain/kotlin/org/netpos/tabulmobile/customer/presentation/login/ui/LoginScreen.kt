package org.netpos.tabulmobile.customer.presentation.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.customer.presentation.login.view_model.LoginScreenIntent
import org.netpos.tabulmobile.customer.presentation.login.view_model.LoginScreenState
import org.netpos.tabulmobile.customer.presentation.login.view_model.LoginScreenViewModel
import org.netpos.tabulmobile.shared.data.emailRegex
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import org.netpos.tabulmobile.shared.presentation.utils.CustomLoadingDialog
import org.netpos.tabulmobile.shared.presentation.utils.TabulButton
import org.netpos.tabulmobile.showToast
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.authenticating_text
import tabulmobile.composeapp.generated.resources.dont_have_account
import tabulmobile.composeapp.generated.resources.email_text
import tabulmobile.composeapp.generated.resources.forgot_password_text
import tabulmobile.composeapp.generated.resources.login_info_text
import tabulmobile.composeapp.generated.resources.login_text
import tabulmobile.composeapp.generated.resources.password_text
import tabulmobile.composeapp.generated.resources.register_text
import tabulmobile.composeapp.generated.resources.remember_me_text
import tabulmobile.composeapp.generated.resources.welcome_back_text

@Composable
fun LoginScreenRoot(navController: NavHostController, loginScreenViewModel: LoginScreenViewModel) {

    val loginViewModelState by loginScreenViewModel.state.collectAsState(initial = LoginScreenState())

    LoginScreen(
        loginViewModelState = loginViewModelState,
        onAction = { intent ->
            loginScreenViewModel.sendIntent(intent)
        },
        navController = navController,
        navigationEvent = loginScreenViewModel.navigationEvent
    )
}

@Composable
fun LoginScreen(
    loginViewModelState: LoginScreenState,
    onAction: (LoginScreenIntent) -> Unit,
    navController: NavHostController,
    navigationEvent: SharedFlow<NavigationRoutes>,
) {

    val coroutineScope = rememberCoroutineScope()
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        navigationEvent.collect { destination ->
            navController.navigate(route = destination)
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth().wrapContentHeight()
                    .padding(start = 16.dp, end = 16.dp),
                tonalElevation = 0.dp,
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        content = {
                            TabulButton(
                                resource = Res.string.login_text,
                                actionClick = {
                                    showToast = true
                                    onAction(LoginScreenIntent.LoginActionClick)
                                },
                                enabled = true
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Text(
                                        text = stringResource(Res.string.dont_have_account),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                            fontSize = 13.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(1.dp))
                                    TextButton(onClick = {
                                        onAction(
                                            LoginScreenIntent.RegisterActionClick
                                        )
                                    }) {
                                        Text(
                                            text = stringResource(Res.string.register_text),
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                                fontSize = 13.sp,
                                                color = tabulColor
                                            )
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
        },
        content = { contentPadding ->
            when {
                loginViewModelState.isLoading -> CustomLoadingDialog(
                    showDialog = loginViewModelState.isLoading,
                    message = stringResource(Res.string.authenticating_text)
                )

                loginViewModelState.responseSuccess -> {
                    onAction(LoginScreenIntent.HomeActionClick)
                }

                loginViewModelState.responseFailed -> {
                    coroutineScope.launch {
                        if (showToast) {
                            showToast(loginViewModelState.errorMessage.toString())
                            showToast = false
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(contentPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart,
                        content = {
                            Column(
                                content = {
                                    Text(
                                        text = stringResource(Res.string.welcome_back_text),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                            fontSize = 25.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = stringResource(Res.string.login_info_text),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = loginViewModelState.email,
                        onValueChange = {
                            onAction(
                                LoginScreenIntent.EmailChanged(
                                    it
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.email_text),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        isError = loginViewModelState.email.isNotBlank() && !emailRegex.matches(
                            loginViewModelState.email
                        ),
                        trailingIcon = {
                            Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.LightGray,
                            errorTextColor = Color.Red,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.onSurface,
                            errorCursorColor = Color.Red,
                            selectionColors = LocalTextSelectionColors.current,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart,
                        content = {
                            loginViewModelState.emailError?.let { errorMessage ->
                                Text(
                                    text = errorMessage,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                        fontSize = 13.sp,
                                        color = Color.Red
                                    )
                                )
                            }
                        }
                    )
                    OutlinedTextField(
                        value = loginViewModelState.password,
                        onValueChange = {
                            onAction(
                                LoginScreenIntent.PasswordChanged(
                                    it
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.password_text),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        isError = loginViewModelState.password.isNotEmpty() && loginViewModelState.password.length < 8,
                        singleLine = true,
                        visualTransformation = if (loginViewModelState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (loginViewModelState.isPasswordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            }
                            IconButton(onClick = {
                                onAction(
                                    LoginScreenIntent.PasswordVisibilityChanged(
                                        !loginViewModelState.isPasswordVisible
                                    )
                                )
                            }) {
                                Icon(imageVector = image, contentDescription = null)
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.LightGray,
                            errorTextColor = Color.Red,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.onSurface,
                            errorCursorColor = Color.Red,
                            selectionColors = LocalTextSelectionColors.current,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart,
                        content = {
                            loginViewModelState.passwordError?.let { errorMessage ->
                                Text(
                                    text = errorMessage,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                        fontSize = 13.sp,
                                        color = Color.Red
                                    )
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Checkbox(
                                        checked = loginViewModelState.rememberMe,
                                        onCheckedChange = {
                                            onAction(
                                                LoginScreenIntent.RememberMeChanged(
                                                    it
                                                )
                                            )
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = tabulColor,
                                            checkmarkColor = Color.White,
                                            uncheckedColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                    Text(
                                        text = stringResource(Res.string.remember_me_text),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                            )
                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    TextButton(
                                        onClick = {
                                            onAction(LoginScreenIntent.ForgotPasswordActionClick)
                                        },
                                        content = {
                                            Text(
                                                text = stringResource(Res.string.forgot_password_text),
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                                    fontSize = 13.sp
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}