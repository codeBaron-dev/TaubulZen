package org.netpos.tabulmobile.customer.data.models.login.remote.login.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseModel(
    @SerialName("data")
    val data: Data?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)