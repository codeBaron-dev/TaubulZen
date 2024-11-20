package org.netpos.tabulmobile.merchant.presentation.login.viewmodels

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val rememberMe: Boolean = false,
    val isPasswordVisible: Boolean = false
)
