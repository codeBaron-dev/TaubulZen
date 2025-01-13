package org.netpos.tabulmobile.customer.data.models.restaurant_details.response.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDetailResponse(
    @SerialName("data")
    val `data`: Data?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)