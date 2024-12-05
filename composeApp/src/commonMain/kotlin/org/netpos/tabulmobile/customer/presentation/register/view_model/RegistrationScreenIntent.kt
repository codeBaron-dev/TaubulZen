package org.netpos.tabulmobile.customer.presentation.register.view_model

sealed class RegistrationScreenIntent {
    data class FullNameChanged(val fullName: String) : RegistrationScreenIntent()
    data class EmailChanged(val email: String) : RegistrationScreenIntent()
    data class PhoneNumberChanged(val phoneNumber: String) : RegistrationScreenIntent()
    data class PasswordChanged(val password: String) : RegistrationScreenIntent()
    data class PasswordVisibilityChanged(val isPasswordVisible: Boolean) : RegistrationScreenIntent()
    data class ConfirmPasswordVisibilityChanged(val isConfirmPasswordVisible: Boolean) : RegistrationScreenIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegistrationScreenIntent()
    data class AcceptTermsChanged(val acceptTerms: Boolean) : RegistrationScreenIntent()
    data class RegisterActionClick(val isDeviceConnectedToInternet: Boolean) : RegistrationScreenIntent()
    data object LoginActionClick : RegistrationScreenIntent()
    data object HomeActionClick : RegistrationScreenIntent()
    data object LocationActionClick : RegistrationScreenIntent()
}