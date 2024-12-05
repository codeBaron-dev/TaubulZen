package org.netpos.tabulmobile.customer.presentation.otp.view_model

import org.netpos.tabulmobile.customer.data.models.otp_verification.remote.response.OtpVerificationResponseModel
import org.netpos.tabulmobile.customer.data.models.password_reset.remote.reponse.PasswordResetResponse

data class OtpVerificationScreenState(
    val otp: String = "",
    val otp1: String = "",
    val otp2: String = "",
    val otp3: String = "",
    val otp4: String = "",
    val otp1Error: Boolean = false,
    val otp2Error: Boolean = false,
    val otp3Error: Boolean = false,
    val otp4Error: Boolean = false,
    val isLoading: Boolean = false,
    val responseSuccessful: Boolean = false,
    val responseFailed: Boolean = false,
    val errorMessage: String? = null,
    val email: String? = null,
    val userEmail: String? = null,
    val successMessage: String? = null,
    val isTimerRunning: Boolean = true,
    val currentTime: Long = 60L,
    val isDeviceConnectedToInternet: Boolean = false,
    val noInternetConnection: Boolean = false,
    val otpVerificationResponseModel: OtpVerificationResponseModel? = null,
    val passwordResetResponse: PasswordResetResponse? = null
)