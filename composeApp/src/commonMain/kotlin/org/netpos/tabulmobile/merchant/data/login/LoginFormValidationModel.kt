package org.netpos.tabulmobile.merchant.data.login

data class LoginFormValidationModel(
    val isEmailValid: Boolean,
    val isPasswordValid: Boolean,
    val emailError: String?,
    val passwordError: String?
)
