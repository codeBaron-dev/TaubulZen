package org.netpos.tabulmobile.merchant.data.login.remote.login.payload


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginPayloadModel(
    @SerialName("email")
    val email: String?,
    @SerialName("password")
    val password: String?
)