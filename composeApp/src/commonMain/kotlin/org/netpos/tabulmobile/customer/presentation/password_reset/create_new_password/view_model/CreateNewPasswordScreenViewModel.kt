package org.netpos.tabulmobile.customer.presentation.password_reset.create_new_password.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.customer.data.models.create_new_password.payload.CreateNewPasswordPayloadModel
import org.netpos.tabulmobile.customer.data.remote.repository.create_new_password.CreateNewPasswordRepository
import org.netpos.tabulmobile.customer.domain.remote.onError
import org.netpos.tabulmobile.customer.domain.remote.onSuccess
import org.netpos.tabulmobile.shared.data.validateCreateNewPasswordForm
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class CreateNewPasswordScreenViewModel(
    private val createNewPasswordRepository: CreateNewPasswordRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow(CreateNewPasswordScreenState())
    val state = _state.asSharedFlow()

    private val intents = MutableSharedFlow<CreateNewPasswordScreenIntent>()

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        handleIntents()
        viewModelScope.launch {
            _state.emit(CreateNewPasswordScreenState())
        }
    }

    fun sendIntent(intent: CreateNewPasswordScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is CreateNewPasswordScreenIntent.ConfirmPasswordChanged -> {
                        updateState { it.copy(confirmPassword = intent.confirmPassword) }
                    }

                    is CreateNewPasswordScreenIntent.CreatePasswordActionClick -> {
                        updateState { it.copy(isDeviceConnectedToInternet = intent.isDeviceConnectedToInternet) }
                        validateCreatePasswordForm()
                    }

                    is CreateNewPasswordScreenIntent.NewPasswordChanged -> {
                        updateState { it.copy(newPassword = intent.newPassword) }
                    }

                    is CreateNewPasswordScreenIntent.ConfirmPasswordVisibilityChanged -> {
                        updateState { it.copy(isConfirmPasswordVisible = intent.isConfirmPasswordVisible) }
                    }

                    is CreateNewPasswordScreenIntent.PasswordVisibilityChanged -> {
                        updateState { it.copy(isPasswordVisible = intent.isPasswordVisible) }
                    }

                    is CreateNewPasswordScreenIntent.UserEmailChanged -> {
                        updateState { it.copy(email = intent.email) }
                    }

                    CreateNewPasswordScreenIntent.LoginActionClick -> {
                        _navigationEvent.emit(NavigationRoutes.Login)
                    }
                }
            }
        }
    }

    private suspend fun validateCreatePasswordForm() {
        val currentState = _state.replayCache.firstOrNull() ?: CreateNewPasswordScreenState()
        val validationResult = validateCreateNewPasswordForm(
            newPassword = currentState.newPassword,
            confirmPassword = currentState.confirmPassword
        )

        if (
            validationResult.isPasswordValid &&
            validationResult.isConfirmPasswordValid &&
            currentState.isDeviceConnectedToInternet
        ) {
            createNewPassword(
                email = currentState.email,
                newPassword = currentState.newPassword,
                confirmPassword = currentState.confirmPassword
            )
        } else {
            updateState {
                it.copy(
                    newPasswordError = validationResult.passwordError,
                    confirmPasswordError = validationResult.confirmPasswordError,
                    noInternetConnection = false
                )
            }
        }
    }

    private fun createNewPassword(email: String, newPassword: String, confirmPassword: String) =
        viewModelScope.launch {
            val createNewPasswordPayloadModel = CreateNewPasswordPayloadModel(
                email = email,
                password = newPassword,
                passwordConfirmation = confirmPassword
            )
            _state.update { it.copy(isLoading = true) }
            createNewPasswordRepository.createNewPassword(
                createNewPasswordPayloadModel = createNewPasswordPayloadModel
            )
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

    private suspend fun updateState(transform: (CreateNewPasswordScreenState) -> CreateNewPasswordScreenState) {
        val currentState = _state.replayCache.firstOrNull() ?: CreateNewPasswordScreenState()
        val newState = transform(currentState)
        _state.emit(newState)
    }

}