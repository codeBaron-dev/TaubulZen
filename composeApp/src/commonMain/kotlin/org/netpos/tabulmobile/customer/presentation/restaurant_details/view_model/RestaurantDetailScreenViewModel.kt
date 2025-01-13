package org.netpos.tabulmobile.customer.presentation.restaurant_details.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.customer.data.remote.repository.restaurant_detail.RestaurantDetailRepository
import org.netpos.tabulmobile.customer.domain.remote.onError
import org.netpos.tabulmobile.customer.domain.remote.onSuccess
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class RestaurantDetailScreenViewModel(private val restaurantDetailRepository: RestaurantDetailRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(RestaurantDetailScreenState())
    val state = _state.asStateFlow()

    private val intents = MutableSharedFlow<RestaurantDetailScreenIntent>()

    private val _navigationIntent = MutableSharedFlow<NavigationRoutes>()
    val navigationIntent = _navigationIntent.asSharedFlow()

    init {
        handleIntent()
        //getRestaurantDetail()
        viewModelScope.launch {
            _state.emit(RestaurantDetailScreenState())
        }
        getRestaurantMenus()
        getRestaurantOpeningHours()
    }

    fun sendIntent(intent: RestaurantDetailScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    RestaurantDetailScreenIntent.FoodItemClicked -> {}
                    RestaurantDetailScreenIntent.OpeningHoursClicked -> {}
                    is RestaurantDetailScreenIntent.ReactionIconClicked -> {}
                    RestaurantDetailScreenIntent.SearchIconClicked -> {}
                    RestaurantDetailScreenIntent.SharedIconClicked -> {}
                    is RestaurantDetailScreenIntent.FetchMenus -> {
                        updateState { it.copy(restaurantId = intent.restaurantId) }
                        getRestaurantMenus()
                    }

                    is RestaurantDetailScreenIntent.FetchMenuCategoryClicked -> {
                        updateState { it.copy(menuId = intent.menuId) }
                        getRestaurantMenuItems()
                    }
                }
            }
        }
    }

    private fun getRestaurantDetail() = viewModelScope.launch {
        val currentState = _state.replayCache.firstOrNull() ?: RestaurantDetailScreenState()
        _state.update { it.copy(isRestaurantLoading = true) }
        restaurantDetailRepository.getRestaurantDetail(restaurantId = currentState.restaurantId)
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        isRestaurantLoading = false,
                        isRestaurantResponseSuccess = true,
                        isRestaurantResponseFailed = false,
                        restaurant = response
                    )
                }
            }
            .onError { message ->
                _state.update {
                    it.copy(
                        isRestaurantLoading = false,
                        isRestaurantResponseSuccess = false,
                        isRestaurantResponseFailed = true,
                        errorMessage = message
                    )
                }
            }
    }

    private fun getRestaurantMenus() = viewModelScope.launch {
        val currentState = _state.replayCache.firstOrNull() ?: RestaurantDetailScreenState()
        _state.update { it.copy(isMenuLoading = true) }
        restaurantDetailRepository.getRestaurantMenus(restaurantId = currentState.restaurantId)
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        isMenuLoading = false,
                        isMenuResponseSuccess = true,
                        isMenuResponseFailed = false,
                        restaurantMenus = response
                    )
                }
            }
            .onError { message ->
                _state.update {
                    it.copy(
                        isMenuLoading = false,
                        isMenuResponseSuccess = false,
                        isMenuResponseFailed = true,
                        errorMessage = message
                    )
                }
            }
    }

    private fun getRestaurantOpeningHours() = viewModelScope.launch {
        val currentState = _state.replayCache.firstOrNull() ?: RestaurantDetailScreenState()
        _state.update { it.copy(isOpeningHoursLoading = true) }
        restaurantDetailRepository.getRestaurantOpeningHours(menuId = "1")
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        isOpeningHoursLoading = false,
                        isOpeningHoursResponseSuccess = true,
                        isOpeningHoursResponseFailed = false,
                        openingHours = response
                    )
                }
            }
            .onError { message ->
                _state.update {
                    it.copy(
                        isOpeningHoursLoading = false,
                        isOpeningHoursResponseSuccess = false,
                        isOpeningHoursResponseFailed = true,
                        errorMessage = message
                    )
                }
            }
    }

    private fun getRestaurantMenuItems() = viewModelScope.launch {
        val currentState = _state.replayCache.firstOrNull() ?: RestaurantDetailScreenState()
        _state.update { it.copy(isMenuCategoriesLoading = true) }
        restaurantDetailRepository.getRestaurantMenuItems(menuId = currentState.menuId)
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        isMenuCategoriesLoading = false,
                        isMenuCategoriesResponseSuccess = true,
                        isMenuCategoriesResponseFailed = false,
                        menuCategories = response
                    )
                }
            }
            .onError { message ->
                _state.update {
                    it.copy(
                        isMenuCategoriesLoading = false,
                        isMenuCategoriesResponseSuccess = false,
                        isMenuCategoriesResponseFailed = true,
                        errorMessage = message
                    )
                }
            }
    }

    private suspend fun updateState(transform: (RestaurantDetailScreenState) -> RestaurantDetailScreenState) {
        val currentState = _state.replayCache.firstOrNull() ?: RestaurantDetailScreenState()
        val newState = transform(currentState)
        _state.emit(newState)
    }
}