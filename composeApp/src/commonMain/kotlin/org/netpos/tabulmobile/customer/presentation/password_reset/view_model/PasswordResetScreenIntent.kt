package org.netpos.tabulmobile.customer.presentation.password_reset.view_model

sealed class PasswordResetScreenIntent {
    data class EmailChanged(val email: String): PasswordResetScreenIntent()
    data class SendEmailActionClick(val isDeviceConnectedToInternet: Boolean) : PasswordResetScreenIntent()
    data object OtpVerificationActionClick : PasswordResetScreenIntent()
}