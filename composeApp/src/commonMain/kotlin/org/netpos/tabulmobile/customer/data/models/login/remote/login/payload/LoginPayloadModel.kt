package org.netpos.tabulmobile.customer.data.models.login.remote.login.payload


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginPayloadModel(
    @SerialName("email")
    val email: String?,
    @SerialName("password")
    val password: String?
)