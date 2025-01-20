package org.netpos.tabulmobile.customer.presentation.basket.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class BasketScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(BasketScreenState())
    val state = _state.asStateFlow()

    private val intents = MutableSharedFlow<BasketScreenIntent>()

    private val _navigationIntent = MutableSharedFlow<NavigationRoutes>()
    val navigationIntent = _navigationIntent.asSharedFlow()

    init {
        handleIntent()
        viewModelScope.launch {
            _state.emit(BasketScreenState())
        }
    }

    fun sendIntent(intent: BasketScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    BasketScreenIntent.AddMoreItems -> {}
                    BasketScreenIntent.CheckoutRestaurantBasket -> {}
                    BasketScreenIntent.DeleteRestaurantBasket -> {}
                    is BasketScreenIntent.RestaurantItemClicked -> {}
                }
            }
        }
    }
}