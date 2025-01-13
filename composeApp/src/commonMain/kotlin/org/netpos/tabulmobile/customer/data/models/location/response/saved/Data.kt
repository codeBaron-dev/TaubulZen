package org.netpos.tabulmobile.customer.data.models.location.response.saved


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("address")
    val address: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("latitude")
    val latitude: String?,
    @SerialName("longitude")
    val longitude: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("user_id")
    val userId: Int?
)