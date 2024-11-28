package org.netpos.tabulmobile.customer.presentation.password_reset.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.customer.presentation.password_reset.view_model.PasswordResetScreenIntent
import org.netpos.tabulmobile.customer.presentation.password_reset.view_model.PasswordResetScreenState
import org.netpos.tabulmobile.customer.presentation.password_reset.view_model.PasswordResetViewModel
import org.netpos.tabulmobile.shared.data.emailRegex
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.presentation.utils.CustomLoadingDialog
import org.netpos.tabulmobile.shared.presentation.utils.TabulButton
import org.netpos.tabulmobile.shared.presentation.utils.TabulTopAppBar
import org.netpos.tabulmobile.showToast
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.authenticating_text
import tabulmobile.composeapp.generated.resources.email_text
import tabulmobile.composeapp.generated.resources.reset_password_info_text
import tabulmobile.composeapp.generated.resources.reset_password_text
import tabulmobile.composeapp.generated.resources.send_verification_code

@Composable
fun PasswordResetScreenRoot(
    navController: NavHostController,
    passwordResetViewModel: PasswordResetViewModel
) {

    val passwordResetViewModelState by passwordResetViewModel.state.collectAsState(initial = PasswordResetScreenState())

    PasswordResetScreen(
        passwordResetViewModelState = passwordResetViewModelState,
        onAction = { intent ->
            passwordResetViewModel.sendIntent(intent)
        },
        navController = navController,
        navigationEvent = passwordResetViewModel.navigationEvent
    )
}

@Composable
fun PasswordResetScreen(
    passwordResetViewModelState: PasswordResetScreenState,
    onAction: (PasswordResetScreenIntent) -> Unit,
    navController: NavHostController,
    navigationEvent: SharedFlow<NavigationRoutes>
) {

    val coroutineScope = rememberCoroutineScope()
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        navigationEvent.collect { destination ->
            navController.navigate(route = destination)
        }
    }

    Scaffold(
        topBar = {
            TabulTopAppBar(
                navigateBack = navController,
                title = stringResource(Res.string.reset_password_text),
                shouldDisplayBackButton = true,
                shouldDisplayTitle = true
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                tonalElevation = 0.dp,
                content = {
                    TabulButton(
                        resource = Res.string.send_verification_code,
                        actionClick = {
                            onAction(PasswordResetScreenIntent.SendEmailActionClick)
                        },
                        enabled = true
                    )
                }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(contentPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    when {
                        passwordResetViewModelState.isLoading -> {
                            CustomLoadingDialog(
                                showDialog = passwordResetViewModelState.isLoading,
                                message = stringResource(Res.string.authenticating_text)
                            )
                        }

                        passwordResetViewModelState.responseSuccess -> {
                            onAction(PasswordResetScreenIntent.OtpVerificationActionClick)
                        }

                        passwordResetViewModelState.responseFailed -> {
                            if (showToast) {
                                showToast(passwordResetViewModelState.errorMessage.toString())
                                showToast = false
                            }
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart,
                        content = {
                            Column(
                                content = {
                                    Text(
                                        text = stringResource(Res.string.reset_password_text),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                            fontSize = 25.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = stringResource(Res.string.reset_password_info_text),
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
                        value = passwordResetViewModelState.email,
                        onValueChange = {
                            onAction(
                                PasswordResetScreenIntent.EmailChanged(
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
                        isError = passwordResetViewModelState.email.isNotBlank() && !emailRegex.matches(
                            passwordResetViewModelState.email
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
                            passwordResetViewModelState.emailError?.let { errorMessage ->
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
                }
            )
        }
    )
}