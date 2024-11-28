package org.netpos.tabulmobile.customer.data.models.password_reset.remote.payload


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetPayloadModel(
    @SerialName("email")
    val email: String?
)