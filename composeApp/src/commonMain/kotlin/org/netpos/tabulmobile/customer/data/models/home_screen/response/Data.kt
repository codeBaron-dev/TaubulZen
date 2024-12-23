package org.netpos.tabulmobile.customer.data.models.home_screen.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("data")
    val data: List<RestaurantInformation>?,
    @SerialName("name")
    val name: String?
)