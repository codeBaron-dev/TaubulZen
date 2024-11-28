package org.netpos.tabulmobile.customer.presentation.password_reset.view_model

sealed class PasswordResetScreenIntent {
    data class EmailChanged(val email: String): PasswordResetScreenIntent()
    data object SendEmailActionClick : PasswordResetScreenIntent()
    data object OtpVerificationActionClick : PasswordResetScreenIntent()
}