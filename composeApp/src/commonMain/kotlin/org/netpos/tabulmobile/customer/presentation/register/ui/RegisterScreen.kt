package org.netpos.tabulmobile.customer.presentation.register.ui

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
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Phone
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
import org.netpos.tabulmobile.ConnectivityCheckerProvider
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorage
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorageImpl
import org.netpos.tabulmobile.customer.presentation.register.view_model.RegistrationScreenIntent
import org.netpos.tabulmobile.customer.presentation.register.view_model.RegistrationScreenState
import org.netpos.tabulmobile.customer.presentation.register.view_model.RegistrationScreenViewModel
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import org.netpos.tabulmobile.shared.presentation.utils.CustomLoadingDialog
import org.netpos.tabulmobile.shared.presentation.utils.TabulButton
import org.netpos.tabulmobile.showToast
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.accept_terms_text
import tabulmobile.composeapp.generated.resources.authenticating_text
import tabulmobile.composeapp.generated.resources.confirm_password_text
import tabulmobile.composeapp.generated.resources.create_account_info_text
import tabulmobile.composeapp.generated.resources.create_account_text
import tabulmobile.composeapp.generated.resources.dont_have_account
import tabulmobile.composeapp.generated.resources.email_text
import tabulmobile.composeapp.generated.resources.full_name_text
import tabulmobile.composeapp.generated.resources.login_text
import tabulmobile.composeapp.generated.resources.no_internet_connection_text
import tabulmobile.composeapp.generated.resources.password_text
import tabulmobile.composeapp.generated.resources.phone_number_text
import tabulmobile.composeapp.generated.resources.register_text

@Composable
fun RegisterScreenRoot(
    navController: NavHostController,
    registerScreenViewModel: RegistrationScreenViewModel
) {
    val registerViewModelState by registerScreenViewModel.state.collectAsState(initial = RegistrationScreenState())

    RegisterScreen(
        registerViewModelState = registerViewModelState,
        onAction = { intent ->
            registerScreenViewModel.sendIntent(intent)
        },
        navController = navController,
        navigationEvent = registerScreenViewModel.navigationEvent
    )
}

@Composable
fun RegisterScreen(
    registerViewModelState: RegistrationScreenState,
    onAction: (RegistrationScreenIntent) -> Unit,
    navController: NavHostController,
    navigationEvent: SharedFlow<NavigationRoutes>,
) {

    val coroutineScope = rememberCoroutineScope()
    var showToast by remember { mutableStateOf(false) }
    val checker = ConnectivityCheckerProvider.getConnectivityChecker()
    var isDeviceConnectedToInternet by remember { mutableStateOf(false) }
    val keyValueStorage: TabulKeyStorage = TabulKeyStorageImpl()

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
                                resource = Res.string.register_text,
                                actionClick = {
                                    coroutineScope.launch {
                                        isDeviceConnectedToInternet =
                                            checker.getConnectivityState().isConnected
                                        onAction(
                                            RegistrationScreenIntent.RegisterActionClick(
                                                isDeviceConnectedToInternet = isDeviceConnectedToInternet
                                            )
                                        )
                                    }
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
                                            RegistrationScreenIntent.LoginActionClick
                                        )
                                    }) {
                                        Text(
                                            text = stringResource(Res.string.login_text),
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
                registerViewModelState.noInternetConnection -> {
                    if (showToast) {
                        showToast(message = stringResource(Res.string.no_internet_connection_text))
                        showToast = false
                    }
                }

                registerViewModelState.isLoading -> CustomLoadingDialog(
                    showDialog = registerViewModelState.isLoading,
                    message = stringResource(Res.string.authenticating_text)
                )

                registerViewModelState.responseSuccess -> {
                    keyValueStorage.email = registerViewModelState.email
                    onAction(RegistrationScreenIntent.LocationActionClick)
                }

                registerViewModelState.responseFailed -> {
                    coroutineScope.launch {
                        if (showToast) {
                            showToast(registerViewModelState.errorMessage.toString())
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
                                        text = stringResource(Res.string.create_account_text),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                            fontSize = 25.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = stringResource(Res.string.create_account_info_text),
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
                        value = registerViewModelState.fullName,
                        onValueChange = {
                            onAction(
                                RegistrationScreenIntent.FullNameChanged(
                                    it
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.full_name_text),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.PersonOutline,
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
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
                            registerViewModelState.fullNameError?.let { errorMessage ->
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
                        value = registerViewModelState.email,
                        onValueChange = {
                            onAction(
                                RegistrationScreenIntent.EmailChanged(
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
                            registerViewModelState.emailError?.let { errorMessage ->
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
                        value = registerViewModelState.phoneNumber,
                        onValueChange = {
                            onAction(
                                RegistrationScreenIntent.PhoneNumberChanged(
                                    it
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.phone_number_text),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            Icon(imageVector = Icons.Outlined.Phone, contentDescription = null)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
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
                            registerViewModelState.phoneNumberError?.let { errorMessage ->
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
                        value = registerViewModelState.password,
                        onValueChange = {
                            onAction(
                                RegistrationScreenIntent.PasswordChanged(
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
                        singleLine = true,
                        visualTransformation = if (registerViewModelState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (registerViewModelState.isPasswordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            }
                            IconButton(onClick = {
                                onAction(
                                    RegistrationScreenIntent.PasswordVisibilityChanged(
                                        !registerViewModelState.isPasswordVisible
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
                            registerViewModelState.passwordError?.let { errorMessage ->
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
                        value = registerViewModelState.confirmPassword,
                        onValueChange = {
                            onAction(
                                RegistrationScreenIntent.ConfirmPasswordChanged(
                                    it
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(Res.string.confirm_password_text),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        visualTransformation = if (registerViewModelState.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (registerViewModelState.isConfirmPasswordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            }
                            IconButton(onClick = {
                                onAction(
                                    RegistrationScreenIntent.ConfirmPasswordVisibilityChanged(
                                        !registerViewModelState.isConfirmPasswordVisible
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
                            registerViewModelState.confirmPasswordError?.let { errorMessage ->
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
                    Spacer(modifier = Modifier.height(20.dp))
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
                                        checked = registerViewModelState.acceptTerms,
                                        onCheckedChange = {
                                            onAction(
                                                RegistrationScreenIntent.AcceptTermsChanged(
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
                                        text = stringResource(Res.string.accept_terms_text),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart,
                        content = {
                            registerViewModelState.acceptTermsError?.let { errorMessage ->
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