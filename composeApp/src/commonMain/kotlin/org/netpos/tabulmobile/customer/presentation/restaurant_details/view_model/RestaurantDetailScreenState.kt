package org.netpos.tabulmobile.customer.presentation.restaurant_details.view_model

import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.details.RestaurantDetailResponse
import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.menu.RestaurantMenus
import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.menu_items.RestaurantMenuItemsResponse
import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.opning_hours.RestaurantOpeningHours

data class RestaurantDetailScreenState (

    val isDeviceConnectedToInternet: Boolean = false,
    val noInternetConnection: Boolean = false,
    val errorMessage: String? = null,

    //restaurant
    val restaurantId: String = "",
    val isRestaurantLoading: Boolean = false,
    val isRestaurantResponseSuccess: Boolean = false,
    val isRestaurantResponseFailed: Boolean = false,
    val restaurant: RestaurantDetailResponse? = null,

    //menu
    val menuId: String = "",
    val isMenuLoading: Boolean = false,
    val isMenuResponseSuccess: Boolean = false,
    val isMenuResponseFailed: Boolean = false,
    val restaurantMenus: RestaurantMenus? = null,

    //menu categories
    val isMenuCategoriesLoading: Boolean = false,
    val isMenuCategoriesResponseSuccess: Boolean = false,
    val isMenuCategoriesResponseFailed: Boolean = false,
    val menuCategories: RestaurantMenuItemsResponse? = null,

    //opening hours
    val isOpeningHoursLoading: Boolean = false,
    val isOpeningHoursResponseSuccess: Boolean = false,
    val isOpeningHoursResponseFailed: Boolean = false,
    val openingHours: RestaurantOpeningHours? = null,
)