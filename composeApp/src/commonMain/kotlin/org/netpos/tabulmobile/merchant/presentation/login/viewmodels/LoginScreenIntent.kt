package org.netpos.tabulmobile.merchant.presentation.login.viewmodels

sealed class LoginScreenIntent {
    data class EmailChanged(val email: String) : LoginScreenIntent()
    data class PasswordChanged(val password: String) : LoginScreenIntent()
    data class RememberMeChanged(val rememberMe: Boolean) : LoginScreenIntent()
    data class PasswordVisibilityChanged(val isPasswordVisible: Boolean) : LoginScreenIntent()
    data object ForgotPasswordActionClick : LoginScreenIntent()
    data object RegisterActionClick : LoginScreenIntent()
    data object LoginActionClick : LoginScreenIntent()
    data object HomeActionClick : LoginScreenIntent()
}