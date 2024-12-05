package org.netpos.tabulmobile.customer.data.models.otp_verification

data class OtpFormValidationModel(
    val isOtp1Valid: Boolean,
    val isOtp2Valid: Boolean,
    val isOtp3Valid: Boolean,
    val isOtp4Valid: Boolean,
)
