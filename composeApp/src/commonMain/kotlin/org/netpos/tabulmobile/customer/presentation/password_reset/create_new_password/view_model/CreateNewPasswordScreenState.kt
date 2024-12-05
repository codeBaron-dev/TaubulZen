package org.netpos.tabulmobile.customer.presentation.password_reset.create_new_password.view_model

import org.netpos.tabulmobile.customer.data.models.password_reset.remote.reponse.PasswordResetResponse

data class CreateNewPasswordScreenState (
    val email: String = "",
    val newPassword: String = "",
    val newPasswordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val responseFailed: Boolean = false,
    val responseSuccess: Boolean = false,
    val errorMessage: String? = null,
    val isDeviceConnectedToInternet: Boolean = false,
    val noInternetConnection: Boolean = false,
    val passwordResetResponse: PasswordResetResponse? = null,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
)