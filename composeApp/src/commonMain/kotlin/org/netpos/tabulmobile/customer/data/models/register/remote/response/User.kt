package org.netpos.tabulmobile.customer.data.models.register.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("updated_at")
    val updatedAt: String?
)