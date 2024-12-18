package org.netpos.tabulmobile.customer.data.remote.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteErrorModel(
    @SerialName("errors")
    val errors: List<String?>?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)