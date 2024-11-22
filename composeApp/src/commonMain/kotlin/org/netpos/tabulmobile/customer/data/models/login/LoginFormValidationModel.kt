package org.netpos.tabulmobile.customer.data.models.login

data class LoginFormValidationModel(
    val isEmailValid: Boolean,
    val isPasswordValid: Boolean,
    val emailError: String?,
    val passwordError: String?
)
