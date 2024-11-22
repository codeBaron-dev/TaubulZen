package org.netpos.tabulmobile.customer.data.models.register.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("token")
    val token: String?,
    @SerialName("user")
    val user: User?
)