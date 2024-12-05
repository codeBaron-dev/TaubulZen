package org.netpos.tabulmobile.customer.presentation.otp.view_model

sealed class OtpVerificationScreenIntent {
    data class Otp1Changed(val otp1: String) : OtpVerificationScreenIntent()
    data class Otp2Changed(val otp2: String) : OtpVerificationScreenIntent()
    data class Otp3Changed(val otp3: String) : OtpVerificationScreenIntent()
    data class Otp4Changed(val otp4: String) : OtpVerificationScreenIntent()
    data class OtpCodeChanged(val otp: String) : OtpVerificationScreenIntent()
    data class CurrentTimeChanged(val time: Long) : OtpVerificationScreenIntent()
    data class IsTimerRunningChanged(val isRunning: Boolean) : OtpVerificationScreenIntent()
    data class VerifyOtpActionClick(
        val userEmail: String,
        val isDeviceConnectedToInternet: Boolean
    ) : OtpVerificationScreenIntent()

    data class ResendOtpActionClick(val email: String, val isDeviceConnectedToInternet: Boolean) :
        OtpVerificationScreenIntent()

    data object CreateNewPasswordActionClick : OtpVerificationScreenIntent()
}