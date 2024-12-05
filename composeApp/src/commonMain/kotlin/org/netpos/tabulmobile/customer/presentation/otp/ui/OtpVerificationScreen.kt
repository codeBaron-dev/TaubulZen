package org.stakeny.stakeny.onboard.presentation.otp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
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
import org.netpos.tabulmobile.customer.presentation.otp.view_model.OtpVerificationScreenIntent
import org.netpos.tabulmobile.customer.presentation.otp.view_model.OtpVerificationScreenState
import org.netpos.tabulmobile.customer.presentation.otp.view_model.OtpVerificationViewModel
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import org.netpos.tabulmobile.shared.presentation.utils.CustomLoadingDialog
import org.netpos.tabulmobile.shared.presentation.utils.SharedOtpTextField
import org.netpos.tabulmobile.shared.presentation.utils.TabulButton
import org.netpos.tabulmobile.shared.presentation.utils.TabulTopAppBar
import org.netpos.tabulmobile.showToast
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.almost_there_text
import tabulmobile.composeapp.generated.resources.authenticating_text
import tabulmobile.composeapp.generated.resources.didnt_receive_otp_code_text
import tabulmobile.composeapp.generated.resources.email_verification_info_text
import tabulmobile.composeapp.generated.resources.email_verification_text
import tabulmobile.composeapp.generated.resources.no_internet_connection_text
import tabulmobile.composeapp.generated.resources.request_new_otp_code_text
import tabulmobile.composeapp.generated.resources.resend_otp_code_text
import tabulmobile.composeapp.generated.resources.verify_otp_text

@Composable
fun OtpVerificationScreenRoot(
    navController: NavHostController,
    otpVerificationViewModel: OtpVerificationViewModel
) {

    val otpVerificationViewModelState by otpVerificationViewModel.state.collectAsState(initial = OtpVerificationScreenState())
    val remainingTime by otpVerificationViewModel.remainingTime.collectAsState()
    val isTimerRunning by otpVerificationViewModel.isTimerRunning.collectAsState()

    OtpVerificationScreen(
        otpVerificationViewModelState = otpVerificationViewModelState,
        onAction = { intent ->
            otpVerificationViewModel.sendIntent(intent)
        },
        navController = navController,
        navigationEvent = otpVerificationViewModel.navigationEvent,
        remainingTime = remainingTime,
        isTimerRunning = isTimerRunning
    )
}

@Composable
fun OtpVerificationScreen(
    otpVerificationViewModelState: OtpVerificationScreenState,
    onAction: (OtpVerificationScreenIntent) -> Unit,
    navController: NavHostController,
    navigationEvent: SharedFlow<NavigationRoutes>,
    remainingTime: Int,
    isTimerRunning: Boolean
) {

    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }

    val coroutineScope = rememberCoroutineScope()
    var showToast by remember { mutableStateOf(false) }
    var isResendOtpTextClicked by remember { mutableStateOf(false) }
    val checker = ConnectivityCheckerProvider.getConnectivityChecker()
    var isDeviceConnectedToInternet by remember { mutableStateOf(false) }
    val keyValueStorage: TabulKeyStorage = TabulKeyStorageImpl()

    LaunchedEffect(key1 = Unit) {
        navigationEvent.collect { destination ->
            navController.navigate(route = destination)
        }
    }

    Scaffold(
        topBar = {
            TabulTopAppBar(
                navigateBack = navController,
                title = stringResource(Res.string.email_verification_text),
                shouldDisplayBackButton = true,
                shouldDisplayTitle = true
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth().height(200.dp)
                    .padding(start = 16.dp, end = 16.dp),
                tonalElevation = 0.dp,
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        content = {
                            TabulButton(
                                resource = Res.string.verify_otp_text,
                                actionClick = {
                                    coroutineScope.launch {
                                        isDeviceConnectedToInternet = checker.getConnectivityState().isConnected
                                        isResendOtpTextClicked = false
                                        onAction(
                                            OtpVerificationScreenIntent.VerifyOtpActionClick(
                                                userEmail = keyValueStorage.email.toString(),
                                                isDeviceConnectedToInternet = isDeviceConnectedToInternet
                                            )
                                        )
                                    }
                                },
                                enabled = isTimerRunning
                            )
                            AnimatedVisibility(
                                visible = !isTimerRunning,
                                enter = fadeIn() + slideInVertically { fullHeight -> fullHeight / 2 },
                                exit = fadeOut() + slideOutVertically { fullHeight -> fullHeight / 2 }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Text(
                                            text = stringResource(Res.string.didnt_receive_otp_code_text),
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                                fontSize = 13.sp
                                            )
                                        )
                                        TextButton(
                                            onClick = {
                                                isResendOtpTextClicked = true
                                                onAction(
                                                    OtpVerificationScreenIntent.ResendOtpActionClick(
                                                        email = keyValueStorage.email.toString(),
                                                        isDeviceConnectedToInternet = isDeviceConnectedToInternet
                                                    )
                                                )
                                            },
                                            content = {
                                                Text(
                                                    text = stringResource(Res.string.resend_otp_code_text),
                                                    style = MaterialTheme.typography.labelMedium.copy(
                                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                                        fontSize = 13.sp,
                                                        color = tabulColor
                                                    )
                                                )
                                            }
                                        )
                                    }
                                )
                            }
                            if (isTimerRunning) Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "${stringResource(Res.string.request_new_otp_code_text)} ${remainingTime / 60}:${
                                    (remainingTime % 60).toString()
                                        .padStart(2, '0')
                                }",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                        }
                    )
                }
            )
        },
        content = { contentPadding ->
            when {
                otpVerificationViewModelState.noInternetConnection -> {
                    if (showToast) {
                        showToast(message = stringResource(Res.string.no_internet_connection_text))
                        showToast = false
                    }
                }

                otpVerificationViewModelState.isLoading -> CustomLoadingDialog(
                    showDialog = otpVerificationViewModelState.isLoading,
                    message = stringResource(Res.string.authenticating_text)
                )

                otpVerificationViewModelState.responseSuccessful -> {
                    if (isResendOtpTextClicked) {
                        coroutineScope.launch {
                            if (showToast) {
                                showToast(otpVerificationViewModelState.passwordResetResponse?.message.toString())
                                showToast = false
                            }
                        }
                    } else {
                        onAction(OtpVerificationScreenIntent.CreateNewPasswordActionClick)
                    }
                }

                otpVerificationViewModelState.responseFailed -> {
                    coroutineScope.launch {
                        if (showToast) {
                            showToast(otpVerificationViewModelState.errorMessage.toString())
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
                                        text = stringResource(Res.string.almost_there_text),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                            fontSize = 25.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = stringResource(Res.string.email_verification_info_text),
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
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SharedOtpTextField(
                            value = otpVerificationViewModelState.otp1,
                            onValueChange = { onAction(OtpVerificationScreenIntent.Otp1Changed(it)) },
                            focusRequester = focusRequester1,
                            nextFocusRequester = focusRequester2,
                            isInputValid = otpVerificationViewModelState.otp1Error
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        SharedOtpTextField(
                            value = otpVerificationViewModelState.otp2,
                            onValueChange = { onAction(OtpVerificationScreenIntent.Otp2Changed(it)) },
                            focusRequester = focusRequester2,
                            nextFocusRequester = focusRequester3,
                            isInputValid = otpVerificationViewModelState.otp2Error
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        SharedOtpTextField(
                            value = otpVerificationViewModelState.otp3,
                            onValueChange = { onAction(OtpVerificationScreenIntent.Otp3Changed(it)) },
                            focusRequester = focusRequester3,
                            nextFocusRequester = focusRequester4,
                            isInputValid = otpVerificationViewModelState.otp3Error
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        SharedOtpTextField(
                            value = otpVerificationViewModelState.otp4,
                            onValueChange = { onAction(OtpVerificationScreenIntent.Otp4Changed(it)) },
                            focusRequester = focusRequester4,
                            isInputValid = otpVerificationViewModelState.otp4Error
                        )
                    }
                }
            )
        }
    )
}