package org.netpos.tabulmobile.customer.data.models.home_screen.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantInformation(
    @SerialName("address")
    val address: String?,
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
    @SerialName("restaurant_id")
    val restaurantId: Int?,
    @SerialName("special_categories")
    val specialCategories: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("website")
    val website: String?
)