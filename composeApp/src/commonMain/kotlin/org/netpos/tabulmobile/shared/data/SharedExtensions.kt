package org.netpos.tabulmobile.shared.data

import org.netpos.tabulmobile.customer.data.models.location.LocationFormValidationModel
import org.netpos.tabulmobile.customer.data.models.login.LoginFormValidationModel
import org.netpos.tabulmobile.customer.data.models.otp_verification.OtpFormValidationModel
import org.netpos.tabulmobile.customer.data.models.password_reset.ResetPasswordEmailFormValidationModel
import org.netpos.tabulmobile.customer.data.models.password_reset.ResetPasswordFormValidationModel
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

fun validateCreateNewPasswordForm(
    newPassword: String,
    confirmPassword: String
): ResetPasswordFormValidationModel {
    val isPasswordValid = newPassword.length >= 8
    val isPasswordConfirmValid = newPassword == confirmPassword
    return ResetPasswordFormValidationModel(
        isPasswordValid = isPasswordValid,
        isConfirmPasswordValid = isPasswordConfirmValid,
        passwordError = if (!isPasswordValid) "Password must be at least 8 characters" else "",
        confirmPasswordError = if (!isPasswordConfirmValid) "Passwords do not match" else ""
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

fun validateResetPasswordEmailForm(email: String): ResetPasswordEmailFormValidationModel {
    val isEmailValid = email.matches(emailRegex)
    return ResetPasswordEmailFormValidationModel(
        isEmailValid = isEmailValid,
        emailError = if (!isEmailValid) "Invalid email format" else ""
    )
}

fun validateLocationForm(address: String): LocationFormValidationModel {
    val isAddressValid = address.isNotEmpty()
    return LocationFormValidationModel(
        isAddressValid = isAddressValid,
        addressError = if (!isAddressValid) "Address cannot be empty" else ""
    )
}

/**
 * Validates a 4-digit OTP form input.
 *
 * @param otp1 The first digit of the OTP.
 * @param otp2 The second digit of the OTP.
 * @param otp3 The third digit of the OTP.
 * @param otp4 The fourth digit of the OTP.
 * @return [OtpFormValidationModel] containing validation status for each OTP field.
 */
fun validateOtpForm(
    otp1: String,
    otp2: String,
    otp3: String,
    otp4: String
): OtpFormValidationModel {
    val isOtp1Valid = otp1.isNotEmpty()
    val isOtp2Valid = otp2.isNotEmpty()
    val isOtp3Valid = otp3.isNotEmpty()
    val isOtp4Valid = otp4.isNotEmpty()

    return OtpFormValidationModel(
        isOtp1Valid = isOtp1Valid,
        isOtp2Valid = isOtp2Valid,
        isOtp3Valid = isOtp3Valid,
        isOtp4Valid = isOtp4Valid
    )
}