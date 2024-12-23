package org.netpos.tabulmobile.customer.presentation.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.customer.data.remote.repository.home.HomeRepository
import org.netpos.tabulmobile.customer.domain.remote.onError
import org.netpos.tabulmobile.customer.domain.remote.onSuccess
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class HomeScreenViewModel(
    private val homeScreenRepository: HomeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val intents = MutableSharedFlow<HomeScreenIntent>()

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        getHomeScreenData()
        handleIntents()
        viewModelScope.launch {
            _state.emit(HomeScreenState())
        }
    }

    fun sendIntent(intent: HomeScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    HomeScreenIntent.AdBannerClicked -> {}
                    is HomeScreenIntent.AddressChanged -> {}
                    HomeScreenIntent.AddressHistoryClicked -> {}
                    HomeScreenIntent.EditLocationClicked -> {}
                    HomeScreenIntent.FilterClicked -> {}
                    HomeScreenIntent.LikedRestaurantClicked -> {}
                    HomeScreenIntent.NotificationClicked -> {}
                    HomeScreenIntent.RestaurantCategoryClicked -> {}
                    HomeScreenIntent.RestaurantClicked -> {}
                    HomeScreenIntent.SearchClicked -> {}
                    HomeScreenIntent.SearchedHistoryClicked -> {}
                }
            }
        }
    }

    private fun getHomeScreenData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        homeScreenRepository.homeScreenRestaurantsInfo()
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        responseSuccess = true,
                        responseFailed = false,
                        homeSpecialRestaurantResponse = response
                    )
                }
            }
            .onError { errorResponse ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        responseSuccess = false,
                        responseFailed = true,
                        errorMessage = errorResponse
                    )
                }
            }
    }

    private suspend fun updateState(transform: (HomeScreenState) -> HomeScreenState) {
        val currentState = _state.replayCache.firstOrNull() ?: HomeScreenState()
        val newState = transform(currentState)
        _state.emit(newState)
    }
}