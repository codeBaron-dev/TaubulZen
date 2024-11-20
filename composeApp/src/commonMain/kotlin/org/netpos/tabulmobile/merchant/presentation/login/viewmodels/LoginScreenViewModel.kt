package org.netpos.tabulmobile.merchant.presentation.login.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.shared.data.validateLoginForm
import org.stakeny.stakeny.shared.domain.navigation.NavigationRoutes

class LoginScreenViewModel : ViewModel() {

    private val _state =
        MutableSharedFlow<LoginScreenState>(replay = 1) // Replay ensures the latest state is emitted to new collectors.
    val state = _state.asSharedFlow()

    private val intents = MutableSharedFlow<LoginScreenIntent>()

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        // Start handling intents
        handleIntents()
        // Emit initial state
        viewModelScope.launch {
            _state.emit(LoginScreenState()) // Emit initial state
        }
    }

    fun sendIntent(intent: LoginScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent) // Emit user intent
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is LoginScreenIntent.EmailChanged -> {
                        updateState { it.copy(email = intent.email) }
                    }

                    is LoginScreenIntent.PasswordChanged -> {
                        updateState { it.copy(password = intent.password) }
                    }

                    is LoginScreenIntent.RememberMeChanged -> {
                        updateState { it.copy(rememberMe = intent.rememberMe) }
                    }

                    LoginScreenIntent.ForgotPasswordActionClick -> {
                        _navigationEvent.emit(NavigationRoutes.ForgotPassword)
                    }

                    LoginScreenIntent.LoginActionClick -> {
                        validateAndSubmitLoginForm()
                    }

                    LoginScreenIntent.RegisterActionClick -> {
                        _navigationEvent.emit(NavigationRoutes.Register)
                    }

                    is LoginScreenIntent.PasswordVisibilityChanged -> {
                        updateState { it.copy(isPasswordVisible = intent.isPasswordVisible) }
                    }
                }
            }
        }
    }

    private suspend fun validateAndSubmitLoginForm() {
        val currentState = _state.replayCache.firstOrNull() ?: LoginScreenState()
        val validationResult = validateLoginForm(currentState.email, currentState.password)

        if (validationResult.isEmailValid && validationResult.isPasswordValid) {
            // Proceed with API call or other login logic
            _navigationEvent.emit(NavigationRoutes.Home) // Example success route
        } else {
            updateState {
                it.copy(
                    emailError = validationResult.emailError,
                    passwordError = validationResult.passwordError
                )
            }
        }
    }

    private suspend fun updateState(transform: (LoginScreenState) -> LoginScreenState) {
        val currentState = _state.replayCache.firstOrNull() ?: LoginScreenState()
        val newState = transform(currentState)
        _state.emit(newState) // Emit the updated state
    }
}