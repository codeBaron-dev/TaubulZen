package org.stakeny.stakeny.onboard.data.model.password_reset

data class ResetPasswordFormValidationModel(
    val isPasswordValid: Boolean,
    val isConfirmPasswordValid: Boolean,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)

data class ResetPasswordEmailFormValidationModel(
    val isEmailValid: Boolean,
    val emailError: String? = null
)
