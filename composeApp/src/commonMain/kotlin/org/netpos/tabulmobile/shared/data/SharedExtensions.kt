package org.netpos.tabulmobile.shared.data

import org.netpos.tabulmobile.merchant.data.login.LoginFormValidationModel

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