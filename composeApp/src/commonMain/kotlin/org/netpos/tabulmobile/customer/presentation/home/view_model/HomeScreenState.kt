package org.netpos.tabulmobile.customer.presentation.home.view_model

import org.netpos.tabulmobile.customer.data.models.home_screen.response.HomeSpecialRestaurantResponse

data class HomeScreenState (
    val isDeviceConnectedToInternet: Boolean = false,
    val noInternetConnection: Boolean = false,
    val isLoading: Boolean = false,
    val responseSuccess: Boolean = false,
    val responseFailed: Boolean = false,
    val errorMessage: String? = null,
    val homeSpecialRestaurantResponse: HomeSpecialRestaurantResponse? = null
)