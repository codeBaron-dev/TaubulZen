package org.netpos.tabulmobile.customer.data.models.home_screen.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeSpecialRestaurantResponse(
    @SerialName("data")
    val data: List<Data>?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)