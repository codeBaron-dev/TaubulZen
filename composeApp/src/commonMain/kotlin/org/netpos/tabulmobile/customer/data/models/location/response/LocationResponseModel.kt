package org.netpos.tabulmobile.customer.data.models.location.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponseModel(
    @SerialName("data")
    val data: Data?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)