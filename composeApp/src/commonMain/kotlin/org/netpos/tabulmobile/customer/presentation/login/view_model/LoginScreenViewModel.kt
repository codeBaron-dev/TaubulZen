package org.netpos.tabulmobile.customer.presentation.login.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.customer.data.models.login.remote.login.payload.LoginPayloadModel
import org.netpos.tabulmobile.customer.data.remote.repository.login.LoginRepository
import org.netpos.tabulmobile.customer.domain.remote.onError
import org.netpos.tabulmobile.customer.domain.remote.onSuccess
import org.netpos.tabulmobile.shared.data.validateLoginForm
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class LoginScreenViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow(LoginScreenState())
    val state = _state.asSharedFlow()

    private val intents = MutableSharedFlow<LoginScreenIntent>()

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        handleIntents()
        viewModelScope.launch {
            _state.emit(LoginScreenState())
        }
    }

    fun sendIntent(intent: LoginScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent)
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

                    LoginScreenIntent.HomeActionClick -> {
                        if (_state.replayCache.firstOrNull()?.responseSuccess == true) {
                            _navigationEvent.emit(NavigationRoutes.Home)
                        }
                    }
                }
            }
        }
    }

    private suspend fun validateAndSubmitLoginForm() {
        val currentState = _state.replayCache.firstOrNull() ?: LoginScreenState()
        val validationResult = validateLoginForm(currentState.email, currentState.password)

        if (validationResult.isEmailValid && validationResult.isPasswordValid) {
            val loginPayloadModel = LoginPayloadModel(
                email = currentState.email,
                password = currentState.password
            )
            login(loginPayloadModel = loginPayloadModel)
        } else {
            updateState {
                it.copy(
                    emailError = validationResult.emailError,
                    passwordError = validationResult.passwordError
                )
            }
        }
    }

    private fun login(loginPayloadModel: LoginPayloadModel) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        loginRepository.login(loginPayloadModel = loginPayloadModel)
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        responseSuccess = true,
                        responseFailed = false,
                        loginResponseModel = response
                    )
                }
            }
            .onError {  errorResponse ->
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

    private suspend fun updateState(transform: (LoginScreenState) -> LoginScreenState) {
        val currentState = _state.replayCache.firstOrNull() ?: LoginScreenState()
        val newState = transform(currentState)
        _state.emit(newState) // Emit the updated state
    }
}