package org.netpos.tabulmobile.shared.data

import org.netpos.tabulmobile.customer.data.models.login.LoginFormValidationModel
import org.netpos.tabulmobile.customer.data.models.register.RegistrationFormValidationModel

val emailRegex = Regex(pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")

fun validateLoginForm(email: String, password: String): LoginFormValidationModel {
    val isEmailValid = email.matches(emailRegex)
    val isPasswordValid = password.length >= 8
    return LoginFormValidationModel(
        isEmailValid = isEmailValid,
        isPasswordValid = isPasswordValid,
        emailError = if (!isEmailValid) "Invalid email format" else "",
        passwordError = if (!isPasswordValid) "Password must be at least 8 characters" else ""
    )
}

fun validateRegistrationForm(
    fullName: String,
    phone: String,
    email: String,
    password: String,
    confirmPassword: String,
    acceptTerms: Boolean
): RegistrationFormValidationModel {
    val isEmailValid = email.matches(emailRegex)
    val isPasswordValid = password.length >= 8
    val isPasswordConfirmValid = password == confirmPassword
    val isFullNameValid = fullName.isNotEmpty()
    val isPhoneValid = phone.isNotEmpty() && phone.length == 11

    return RegistrationFormValidationModel(
        isEmailValid = isEmailValid,
        emailError = if (!isEmailValid) "Invalid email format" else "",
        isPasswordValid = isPasswordValid,
        passwordError = if (!isPasswordValid) "Password must be at least 8 characters" else "",
        isConfirmPasswordValid = isPasswordConfirmValid,
        confirmPasswordError = if (!isPasswordConfirmValid) "Passwords do not match" else "",
        isFullNameValid = isFullNameValid,
        fullNameError = if (!isFullNameValid) "First name cannot be empty" else "",
        isPhoneNumberValid = isPhoneValid,
        phoneNumberError = if (!isPhoneValid) "Invalid phone number" else "",
        acceptTerms = acceptTerms,
        acceptTermsError = if (!acceptTerms) "You must accept the terms and conditions" else ""
    )
}