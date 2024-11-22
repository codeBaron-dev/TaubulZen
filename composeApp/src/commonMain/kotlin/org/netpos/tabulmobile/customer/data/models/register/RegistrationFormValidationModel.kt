package org.netpos.tabulmobile.customer.data.models.register

data class RegistrationFormValidationModel(
    val isEmailValid: Boolean,
    val emailError: String?,
    val isPasswordValid: Boolean,
    val passwordError: String?,
    val isConfirmPasswordValid: Boolean,
    val confirmPasswordError: String?,
    val isFullNameValid: Boolean,
    val fullNameError: String?,
    val isPhoneNumberValid: Boolean,
    val phoneNumberError: String?,
    val acceptTerms: Boolean,
    val acceptTermsError: String?
)
