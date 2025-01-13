package org.netpos.tabulmobile.customer.data.models.restaurant_details.response.menu_items


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("category")
    val category: String?,
    @SerialName("category_id")
    val categoryId: Int?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("menu_image")
    val menuImage: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("price")
    val price: String?,
    @SerialName("restaurant_id")
    val restaurantId: Int?,
    @SerialName("updated_at")
    val updatedAt: String?
)