package org.netpos.tabulmobile.customer.data.models.restaurant_details.response.menu


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("category_id")
    val categoryId: Int?,
    @SerialName("category_name")
    val categoryName: String?,
    @SerialName("restaurant_id")
    val restaurantId: Int?
)