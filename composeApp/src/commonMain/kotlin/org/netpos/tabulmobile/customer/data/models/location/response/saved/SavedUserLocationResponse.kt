package org.netpos.tabulmobile.customer.data.models.location.response.saved


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavedUserLocationResponse(
    @SerialName("data")
    val data: List<Data?>?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)