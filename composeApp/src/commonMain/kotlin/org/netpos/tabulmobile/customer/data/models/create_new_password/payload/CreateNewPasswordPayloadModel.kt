package org.netpos.tabulmobile.customer.data.models.create_new_password.payload


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateNewPasswordPayloadModel(
    @SerialName("email")
    val email: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("password_confirmation")
    val passwordConfirmation: String?
)