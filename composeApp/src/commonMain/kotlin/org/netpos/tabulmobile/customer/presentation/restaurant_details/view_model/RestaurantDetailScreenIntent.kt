package org.netpos.tabulmobile.customer.presentation.restaurant_details.view_model

sealed class RestaurantDetailScreenIntent {
    data class ReactionIconClicked(val isLiked: Boolean) : RestaurantDetailScreenIntent()
    data object SharedIconClicked : RestaurantDetailScreenIntent()
    data object SearchIconClicked : RestaurantDetailScreenIntent()
    data object FoodItemClicked : RestaurantDetailScreenIntent()
    data object OpeningHoursClicked : RestaurantDetailScreenIntent()
    data class FetchMenus(val restaurantId: String) : RestaurantDetailScreenIntent()
    data class FetchMenuCategoryClicked(val menuId: String) : RestaurantDetailScreenIntent()
}