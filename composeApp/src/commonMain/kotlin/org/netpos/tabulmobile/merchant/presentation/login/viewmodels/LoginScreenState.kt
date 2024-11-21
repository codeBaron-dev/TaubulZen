package org.netpos.tabulmobile.merchant.presentation.login.viewmodels

import org.netpos.tabulmobile.merchant.data.login.remote.login.response.LoginResponseModel

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val responseSuccess: Boolean = false,
    val responseFailed: Boolean = false,
    val errorMessage: String? = null,
    val rememberMe: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val loginResponseModel: LoginResponseModel? = null
)
