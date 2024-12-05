package org.netpos.tabulmobile.customer.presentation.password_reset.create_new_password.view_model

sealed class CreateNewPasswordScreenIntent {
    data class NewPasswordChanged(val newPassword: String): CreateNewPasswordScreenIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String): CreateNewPasswordScreenIntent()
    data class UserEmailChanged(val email: String): CreateNewPasswordScreenIntent()
    data class CreatePasswordActionClick(val isDeviceConnectedToInternet: Boolean) : CreateNewPasswordScreenIntent()
    data object LoginActionClick : CreateNewPasswordScreenIntent()
    data class PasswordVisibilityChanged(val isPasswordVisible: Boolean) : CreateNewPasswordScreenIntent()
    data class ConfirmPasswordVisibilityChanged(val isConfirmPasswordVisible: Boolean) : CreateNewPasswordScreenIntent()
}