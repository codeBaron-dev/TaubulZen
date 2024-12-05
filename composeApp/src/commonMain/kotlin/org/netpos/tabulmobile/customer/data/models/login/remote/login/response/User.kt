package org.netpos.tabulmobile.customer.data.models.login.remote.login.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerialName("fullname")
    val fullname: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("is_active")
    val isActive: Int?,
    @SerialName("password_reset_expires_at")
    val passwordResetExpiresAt: String?,
    @SerialName("password_reset_token")
    val passwordResetToken: String?,
    @SerialName("phone_number")
    val phoneNumber: String?,
    @SerialName("profile_image")
    val profileImage: String?,
    @SerialName("role")
    val role: String?,
    @SerialName("updated_at")
    val updatedAt: String?
)