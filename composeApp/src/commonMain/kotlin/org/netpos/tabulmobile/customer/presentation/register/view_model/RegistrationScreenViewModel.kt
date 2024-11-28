package org.netpos.tabulmobile.customer.presentation.register.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.customer.data.models.register.remote.payload.RegistrationPayloadModel
import org.netpos.tabulmobile.customer.data.remote.repository.register.RegistrationRepository
import org.netpos.tabulmobile.customer.domain.remote.onError
import org.netpos.tabulmobile.customer.domain.remote.onSuccess
import org.netpos.tabulmobile.shared.data.validateRegistrationForm
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class RegistrationScreenViewModel(
    private val registrationRepository: RegistrationRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow(RegistrationScreenState())
    val state = _state.asSharedFlow()

    private val intents = MutableSharedFlow<RegistrationScreenIntent>()

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        handleIntents()
        viewModelScope.launch {
            _state.emit(RegistrationScreenState())
        }
    }

    fun sendIntent(intent: RegistrationScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is RegistrationScreenIntent.AcceptTermsChanged -> {
                        updateState { it.copy(acceptTerms = intent.acceptTerms) }
                    }

                    is RegistrationScreenIntent.ConfirmPasswordChanged -> {
                        updateState { it.copy(confirmPassword = intent.confirmPassword) }
                    }

                    is RegistrationScreenIntent.EmailChanged -> {
                        updateState { it.copy(email = intent.email) }
                    }

                    is RegistrationScreenIntent.FullNameChanged -> {
                        updateState { it.copy(fullName = intent.fullName) }
                    }

                    RegistrationScreenIntent.LoginActionClick -> {
                        _navigationEvent.emit(value = NavigationRoutes.Login)
                    }

                    is RegistrationScreenIntent.PasswordChanged -> {
                        updateState { it.copy(password = intent.password) }
                    }

                    is RegistrationScreenIntent.PhoneNumberChanged -> {
                        updateState { it.copy(phoneNumber = intent.phoneNumber) }
                    }

                    RegistrationScreenIntent.RegisterActionClick -> {
                        validateAndSubmitRegistrationForm()
                    }

                    is RegistrationScreenIntent.ConfirmPasswordVisibilityChanged -> {
                        updateState { it.copy(isConfirmPasswordVisible = intent.isConfirmPasswordVisible) }
                    }
                    is RegistrationScreenIntent.PasswordVisibilityChanged -> {
                        updateState { it.copy(isPasswordVisible = intent.isPasswordVisible) }
                    }

                    RegistrationScreenIntent.HomeActionClick -> {
                        _navigationEvent.emit(value = NavigationRoutes.Home)
                    }
                }
            }
        }
    }

    private suspend fun validateAndSubmitRegistrationForm() {
        val currentState = _state.replayCache.firstOrNull() ?: RegistrationScreenState()
        val validationResult = validateRegistrationForm(
            fullName = currentState.fullName,
            phone = currentState.phoneNumber,
            email = currentState.email,
            password = currentState.password,
            confirmPassword = currentState.confirmPassword,
            acceptTerms = currentState.acceptTerms
        )

        if (validationResult.isEmailValid &&
            validationResult.isPasswordValid &&
            validationResult.isConfirmPasswordValid &&
            validationResult.isFullNameValid &&
            validationResult.isPhoneNumberValid &&
            validationResult.acceptTerms
        ) {
            val registrationPayloadModel = RegistrationPayloadModel(
                fullName = currentState.fullName,
                email = currentState.email,
                phoneNumber = currentState.phoneNumber,
                password = currentState.password,
                passwordConfirmation = currentState.confirmPassword
            )
            register(registrationPayloadModel = registrationPayloadModel)
        } else {
            updateState {
                it.copy(
                    fullNameError = validationResult.fullNameError,
                    phoneNumberError = validationResult.phoneNumberError,
                    confirmPasswordError = validationResult.confirmPasswordError,
                    emailError = validationResult.emailError,
                    passwordError = validationResult.passwordError,
                    acceptTermsError = validationResult.acceptTermsError
                )
            }
        }
    }

    private fun register(registrationPayloadModel: RegistrationPayloadModel) =
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            registrationRepository.register(registrationPayloadModel = registrationPayloadModel)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            responseSuccess = true,
                            responseFailed = false,
                            registrationResponseModel = response
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            responseSuccess = false,
                            responseFailed = true,
                            errorMessage = it.errorMessage
                        )
                    }
                }
        }

    private suspend fun updateState(transform: (RegistrationScreenState) -> RegistrationScreenState) {
        val currentState = _state.replayCache.firstOrNull() ?: RegistrationScreenState()
        val newState = transform(currentState)
        _state.emit(newState) // Emit the updated state
    }
}