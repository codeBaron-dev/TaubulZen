package org.netpos.tabulmobile.customer.data.models.restaurant_details.response.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpeningHours(
    @SerialName("Mon-Fri")
    val monFri: String?,
    @SerialName("Sat-Sun")
    val satSun: String?
)