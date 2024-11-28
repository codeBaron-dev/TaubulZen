package org.netpos.tabulmobile.customer.presentation.password_reset.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.customer.data.models.password_reset.remote.payload.PasswordResetPayloadModel
import org.netpos.tabulmobile.customer.data.remote.repository.password_reset.PasswordResetRepository
import org.netpos.tabulmobile.customer.domain.remote.onError
import org.netpos.tabulmobile.customer.domain.remote.onSuccess
import org.netpos.tabulmobile.shared.data.validateResetPasswordEmailForm
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class PasswordResetViewModel(
    private val passwordResetRepository: PasswordResetRepository
) : ViewModel() {
    private val _state =
        MutableStateFlow(PasswordResetScreenState())
    val state = _state.asSharedFlow()

    private val intents = MutableSharedFlow<PasswordResetScreenIntent>()

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        handleIntents()
        viewModelScope.launch {
            _state.emit(PasswordResetScreenState())
        }
    }

    fun sendIntent(intent: PasswordResetScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is PasswordResetScreenIntent.EmailChanged -> {
                        updateState { it.copy(email = intent.email) }
                    }

                    PasswordResetScreenIntent.SendEmailActionClick -> {
                        validateAndSubmitEmailForm()
                    }

                    PasswordResetScreenIntent.OtpVerificationActionClick -> {
                        if (_state.replayCache.firstOrNull()?.responseSuccess == true) {
                            _navigationEvent.emit(value = NavigationRoutes.OtpVerification)
                        }
                    }
                }
            }
        }
    }

    private suspend fun validateAndSubmitEmailForm() {
        val currentState = _state.replayCache.firstOrNull() ?: PasswordResetScreenState()
        val validationResult = validateResetPasswordEmailForm(currentState.email)

        if (validationResult.isEmailValid) {
            forgetPassword(email = currentState.email)
        } else {
            updateState {
                it.copy(
                    emailError = validationResult.emailError
                )
            }
        }
    }

    private fun forgetPassword(email: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val passwordResetPayloadModel = PasswordResetPayloadModel(email = email)
        passwordResetRepository.forgotPassword(passwordResetPayloadModel = passwordResetPayloadModel)
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        responseSuccess = true,
                        responseFailed = false,
                        passwordResetResponse = response
                    )
                }
            }
            .onError { errorResponse ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        responseSuccess = false,
                        responseFailed = true,
                        errorMessage = errorResponse
                    )
                }
            }
    }

    private suspend fun updateState(transform: (PasswordResetScreenState) -> PasswordResetScreenState) {
        val currentState = _state.replayCache.firstOrNull() ?: PasswordResetScreenState()
        val newState = transform(currentState)
        _state.emit(newState)
    }
}