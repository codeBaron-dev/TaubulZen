package org.netpos.tabulmobile.customer.data.models.register.remote.payload


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationPayloadModel(
    @SerialName("email")
    val email: String?,
    @SerialName("fullname")
    val fullName: String?,
    @SerialName("phone_number")
    val phoneNumber: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("password_confirmation")
    val passwordConfirmation: String?
)