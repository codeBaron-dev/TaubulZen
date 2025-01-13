package org.netpos.tabulmobile.customer.data.models.restaurant_details.response.menu


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantMenus(
    @SerialName("data")
    val data: List<Data?>?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)