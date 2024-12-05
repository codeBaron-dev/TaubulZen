package org.netpos.tabulmobile.customer.data.models.location.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("address")
    val address: String?,
    @SerialName("latitude")
    val latitude: String?,
    @SerialName("longitude")
    val longitude: String?,
    @SerialName("user_id")
    val userId: Int?
)