package org.netpos.tabulmobile.customer.data.models.password_reset.remote.reponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetResponse(
    @SerialName("data")
    val data: String?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)