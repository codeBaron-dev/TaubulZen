package org.netpos.tabulmobile.customer.presentation.register.view_model

import org.netpos.tabulmobile.customer.data.models.register.remote.response.RegistrationResponseModel

data class RegistrationScreenState(
    val fullName: String = "",
    val fullNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val acceptTerms: Boolean = false,
    val acceptTermsError: String? = null,
    val isLoading: Boolean = false,
    val responseSuccess: Boolean = false,
    val responseFailed: Boolean = false,
    val errorMessage: String? = null,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val registrationResponseModel: RegistrationResponseModel? = null,
)