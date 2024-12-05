package org.netpos.tabulmobile.customer.presentation.password_reset.view_model

import org.netpos.tabulmobile.customer.data.models.password_reset.remote.reponse.PasswordResetResponse

data class PasswordResetScreenState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val responseSuccess: Boolean = false,
    val responseFailed: Boolean = false,
    val errorMessage: String? = null,
    val isDeviceConnectedToInternet: Boolean = false,
    val noInternetConnection: Boolean = false,
    val passwordResetResponse: PasswordResetResponse? = null
)