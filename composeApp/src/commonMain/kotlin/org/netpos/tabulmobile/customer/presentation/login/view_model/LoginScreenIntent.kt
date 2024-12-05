package org.netpos.tabulmobile.customer.presentation.login.view_model

sealed class LoginScreenIntent {
    data class EmailChanged(val email: String) : LoginScreenIntent()
    data class PasswordChanged(val password: String) : LoginScreenIntent()
    data class RememberMeChanged(val rememberMe: Boolean) : LoginScreenIntent()
    data class PasswordVisibilityChanged(val isPasswordVisible: Boolean) : LoginScreenIntent()
    data object ForgotPasswordActionClick : LoginScreenIntent()
    data object RegisterActionClick : LoginScreenIntent()
    data class LoginActionClick(val isDeviceConnectedToInternet: Boolean) : LoginScreenIntent()
    data object HomeActionClick : LoginScreenIntent()
}