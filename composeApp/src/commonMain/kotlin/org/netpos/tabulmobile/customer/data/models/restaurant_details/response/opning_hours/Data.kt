package org.netpos.tabulmobile.customer.data.models.restaurant_details.response.opning_hours


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("close")
    val close: String?,
    @SerialName("day")
    val day: String?,
    @SerialName("open")
    val open: String?
)