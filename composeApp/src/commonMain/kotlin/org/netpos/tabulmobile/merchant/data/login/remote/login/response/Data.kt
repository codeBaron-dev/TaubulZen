package org.netpos.tabulmobile.merchant.data.login.remote.login.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("token")
    val token: String?,
    @SerialName("user")
    val user: User?
)