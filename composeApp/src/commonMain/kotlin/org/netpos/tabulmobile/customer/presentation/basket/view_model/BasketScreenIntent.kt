package org.netpos.tabulmobile.customer.presentation.basket.view_model

sealed class BasketScreenIntent {
    data object DeleteRestaurantBasket: BasketScreenIntent()
    data object CheckoutRestaurantBasket: BasketScreenIntent()
    data object AddMoreItems: BasketScreenIntent()
    data class RestaurantItemClicked(val restaurantId: String): BasketScreenIntent()
}