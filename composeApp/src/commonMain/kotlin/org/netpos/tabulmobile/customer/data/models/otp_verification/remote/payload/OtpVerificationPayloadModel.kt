package org.netpos.tabulmobile.customer.data.models.otp_verification.remote.payload


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpVerificationPayloadModel(
    @SerialName("email")
    val email: String?,
    @SerialName("otp")
    val otp: String?
)