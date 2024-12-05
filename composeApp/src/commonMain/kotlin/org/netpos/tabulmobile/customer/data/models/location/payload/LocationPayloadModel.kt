package org.netpos.tabulmobile.customer.data.models.location.payload


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationPayloadModel(
    @SerialName("address")
    val address: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("latitude")
    val latitude: String?,
    @SerialName("longitude")
    val longitude: String?
)