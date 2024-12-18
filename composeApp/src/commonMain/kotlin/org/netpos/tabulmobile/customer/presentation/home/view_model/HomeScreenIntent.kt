package org.netpos.tabulmobile.customer.presentation.home.view_model

sealed class HomeScreenIntent {
    data object NotificationClicked : HomeScreenIntent()
    data object EditLocationClicked : HomeScreenIntent()
    data object FilterClicked : HomeScreenIntent()
    data object SearchClicked : HomeScreenIntent()
    data object RestaurantClicked : HomeScreenIntent()
    data object RestaurantCategoryClicked : HomeScreenIntent()
    data object LikedRestaurantClicked : HomeScreenIntent()
    data class AddressChanged(val address: String) : HomeScreenIntent()
    data object AddressHistoryClicked : HomeScreenIntent()
    data object SearchedHistoryClicked : HomeScreenIntent()
    data object AdBannerClicked : HomeScreenIntent()
}