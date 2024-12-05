package org.netpos.tabulmobile.customer.presentation.otp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.customer.data.models.otp_verification.remote.payload.OtpVerificationPayloadModel
import org.netpos.tabulmobile.customer.data.models.password_reset.remote.payload.PasswordResetPayloadModel
import org.netpos.tabulmobile.customer.data.remote.repository.otp_verification.OtpVerificationRepository
import org.netpos.tabulmobile.customer.data.remote.repository.password_reset.PasswordResetRepository
import org.netpos.tabulmobile.customer.domain.remote.onError
import org.netpos.tabulmobile.customer.domain.remote.onSuccess
import org.netpos.tabulmobile.shared.data.validateOtpForm
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class OtpVerificationViewModel(
    private val otpVerificationRepository: OtpVerificationRepository,
    private val passwordResetRepository: PasswordResetRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow(OtpVerificationScreenState())
    val state = _state.asSharedFlow()

    private val intents = MutableSharedFlow<OtpVerificationScreenIntent>()

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _remainingTime = MutableStateFlow(60) // Initial countdown time in seconds
    val remainingTime: StateFlow<Int> get() = _remainingTime

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> get() = _isTimerRunning

    private var timerJob: Job? = null


    fun startTimer() {
        if (!_isTimerRunning.value) {
            _isTimerRunning.value = true
            timerJob = viewModelScope.launch {
                while (_remainingTime.value > 0) {
                    delay(1000L) // 1-second delay
                    _remainingTime.value -= 1
                }
                _isTimerRunning.value = false // Timer stopped
            }
        }
    }

    fun restartTimer() {
        timerJob?.cancel() // Cancel any existing timer
        _remainingTime.value = 60 // Reset time
        _isTimerRunning.value = false
        startTimer()
    }

    init {
        startTimer()
        handleIntents()
        viewModelScope.launch {
            _state.emit(OtpVerificationScreenState())
        }
    }

    fun sendIntent(intent: OtpVerificationScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is OtpVerificationScreenIntent.CurrentTimeChanged -> {
                        updateState { it.copy(currentTime = intent.time) }
                    }

                    is OtpVerificationScreenIntent.IsTimerRunningChanged -> {
                        updateState { it.copy(isTimerRunning = intent.isRunning) }
                    }

                    is OtpVerificationScreenIntent.Otp1Changed -> {
                        updateState { it.copy(otp1 = intent.otp1) }
                    }

                    is OtpVerificationScreenIntent.Otp2Changed -> {
                        updateState { it.copy(otp2 = intent.otp2) }
                    }

                    is OtpVerificationScreenIntent.Otp3Changed -> {
                        updateState { it.copy(otp3 = intent.otp3) }
                    }

                    is OtpVerificationScreenIntent.Otp4Changed -> {
                        updateState { it.copy(otp4 = intent.otp4) }
                    }

                    is OtpVerificationScreenIntent.OtpCodeChanged -> {
                        updateState { it.copy(otp = intent.otp) }
                    }

                    is OtpVerificationScreenIntent.ResendOtpActionClick -> {
                        updateState {
                            it.copy(
                                email = intent.email,
                                isDeviceConnectedToInternet = intent.isDeviceConnectedToInternet
                            )
                        }
                        restartTimer()
                        forgetPassword()
                    }

                    is OtpVerificationScreenIntent.VerifyOtpActionClick -> {
                        updateState {
                            it.copy(
                                email = intent.userEmail,
                                isDeviceConnectedToInternet = intent.isDeviceConnectedToInternet
                            )
                        }
                        validateOtp()
                    }

                    OtpVerificationScreenIntent.CreateNewPasswordActionClick -> {
                        _navigationEvent.emit(NavigationRoutes.CreateNewPassword)
                    }
                }
            }
        }
    }

    private suspend fun validateOtp() {
        val currentState = _state.replayCache.firstOrNull() ?: OtpVerificationScreenState()
        val validationResult = validateOtpForm(
            otp1 = currentState.otp1,
            otp2 = currentState.otp2,
            otp3 = currentState.otp3,
            otp4 = currentState.otp4
        )
        if (
            validationResult.isOtp1Valid &&
            validationResult.isOtp2Valid &&
            validationResult.isOtp3Valid &&
            validationResult.isOtp4Valid &&
            currentState.isDeviceConnectedToInternet
        ) {
            val otp =
                "${currentState.otp1}${currentState.otp2}${currentState.otp3}${currentState.otp4}"
            val otpVerificationPayloadModel = OtpVerificationPayloadModel(
                email = currentState.userEmail,
                otp = otp
            )
            otpVerification(otpVerificationPayloadModel = otpVerificationPayloadModel)
        } else {
            updateState {
                it.copy(
                    otp1Error = validationResult.isOtp1Valid,
                    otp2Error = validationResult.isOtp2Valid,
                    otp3Error = validationResult.isOtp3Valid,
                    otp4Error = validationResult.isOtp4Valid,
                    noInternetConnection = false
                )
            }
        }
    }

    private fun otpVerification(
        otpVerificationPayloadModel: OtpVerificationPayloadModel
    ) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        otpVerificationRepository.otpVerification(otpVerificationPayloadModel = otpVerificationPayloadModel)
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        responseSuccessful = true,
                        responseFailed = false,
                        otpVerificationResponseModel = response
                    )
                }
            }
            .onError { errorResponse ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        responseSuccessful = false,
                        responseFailed = true,
                        errorMessage = errorResponse
                    )
                }
            }
    }

    private fun forgetPassword() = viewModelScope.launch {
        val currentState = _state.replayCache.firstOrNull() ?: OtpVerificationScreenState()
        if (currentState.isDeviceConnectedToInternet) {
            _state.update { it.copy(isLoading = true) }
            val passwordResetPayloadModel = PasswordResetPayloadModel(email = currentState.email)
            passwordResetRepository.forgotPassword(passwordResetPayloadModel = passwordResetPayloadModel)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            responseSuccessful = true,
                            responseFailed = false,
                            passwordResetResponse = response
                        )
                    }
                }
                .onError { errorResponse ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            responseSuccessful = false,
                            responseFailed = true,
                            errorMessage = errorResponse
                        )
                    }
                }
        } else {
            updateState {
                it.copy(
                    isDeviceConnectedToInternet = false
                )
            }
        }
    }

    private suspend fun updateState(transform: (OtpVerificationScreenState) -> OtpVerificationScreenState) {
        val currentState = _state.replayCache.firstOrNull() ?: OtpVerificationScreenState()
        val newState = transform(currentState)
        _state.emit(newState)
    }
}