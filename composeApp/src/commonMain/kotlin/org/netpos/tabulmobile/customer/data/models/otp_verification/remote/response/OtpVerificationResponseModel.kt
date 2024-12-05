package org.netpos.tabulmobile.customer.data.models.otp_verification.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpVerificationResponseModel(
    @SerialName("data")
    val data: String?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)