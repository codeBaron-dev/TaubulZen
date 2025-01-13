package org.netpos.tabulmobile.customer.data.models.restaurant_details.response.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("address")
    val address: String?,
    @SerialName("contact_number")
    val contactNumber: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("discount_percentage")
    val discountPercentage: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("is_on_promo")
    val isOnPromo: Int?,
    @SerialName("latitude")
    val latitude: String?,
    @SerialName("longitude")
    val longitude: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("opening_hours")
    val openingHours: OpeningHours?,
    @SerialName("rating")
    val rating: String?,
    @SerialName("restaurant_detail_image")
    val restaurantDetailImage: String?,
    @SerialName("restaurant_landing_image")
    val restaurantLandingImage: String?,
    @SerialName("special_categories")
    val specialCategories: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("website")
    val website: String?
)