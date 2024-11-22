package org.netpos.tabulmobile.customer.data.models.register.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponseModel(
    @SerialName("data")
    val data: Data?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)