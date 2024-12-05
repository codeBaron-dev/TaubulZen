package org.netpos.tabulmobile.customer.data.models.register.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("fullname")
    val fullName: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("updated_at")
    val updatedAt: String?
)