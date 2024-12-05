package org.netpos.tabulmobile.shared.presentation.location.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jordond.compass.Priority
import dev.jordond.compass.autocomplete.Autocomplete
import dev.jordond.compass.autocomplete.mobile
import dev.jordond.compass.geocoder.MobileGeocoder
import dev.jordond.compass.geocoder.placeOrNull
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.Locator
import dev.jordond.compass.geolocation.mobile.mobile
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.customer.data.models.location.payload.LocationPayloadModel
import org.netpos.tabulmobile.customer.data.remote.repository.location.LocationRepository
import org.netpos.tabulmobile.customer.domain.remote.onError
import org.netpos.tabulmobile.customer.domain.remote.onSuccess
import org.netpos.tabulmobile.shared.data.validateLocationForm
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class LocationScreenViewModel(
    private val locationRepository: LocationRepository
) : ViewModel() {

    val locator: Locator = Locator.mobile()
    private val geoLocation = Geolocator(locator = locator)
    private val autocomplete = Autocomplete.mobile()

    private val _state =
        MutableStateFlow(LocationScreenState())
    val state = _state.asSharedFlow()

    private val intents = MutableSharedFlow<LocationScreenIntent>()

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        handleIntents()
        viewModelScope.launch {
            _state.emit(LocationScreenState())
        }
    }

    fun sendIntent(intent: LocationScreenIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is LocationScreenIntent.AddressChanged -> {
                        updateState { it.copy(address = intent.address) }
                    }

                    is LocationScreenIntent.SaveLocationActionClick -> {
                        updateState {
                            it.copy(
                                email = intent.email,
                                isDeviceConnectedToInternet = intent.isDeviceConnectedToInternet
                            )
                        }
                        validateForm()
                    }

                    is LocationScreenIntent.SearchActionClick -> {
                        updateState { it.copy(isDeviceConnectedToInternet = intent.isDeviceConnectedToInternet) }
                    }

                    is LocationScreenIntent.SearchedAddressChanged -> {
                        updateState { it.copy(searchedAddress = intent.searchedAddress) }
                    }

                    LocationScreenIntent.AutoFetchLocation -> autoFetchLocation()
                    is LocationScreenIntent.OnSearchAction -> {
                        updateState { it.copy(searchQuery = intent.query) }
                        onSearchQueryChanged()
                    }
                    LocationScreenIntent.OnSearchQueryAction -> {
                        onSearch()
                    }

                    is LocationScreenIntent.OnPlaceSelected -> {
                        updateState { it.copy(selectedPlace = intent.place) }
                        onPlaceSelected()
                    }

                    LocationScreenIntent.OnDropdownDismissRequest -> onDropdownDismissRequest()
                    LocationScreenIntent.HomeActionClick -> {
                        _navigationEvent.emit(value = NavigationRoutes.Home)
                    }
                }
            }
        }
    }

    private fun autoFetchLocation() {
        viewModelScope.launch {
            when (val result = geoLocation.current(priority = Priority.HighAccuracy)) {
                is GeolocatorResult.Error -> {
                    val errorMessage = when (result) {
                        is GeolocatorResult.NotSupported -> result.message
                        is GeolocatorResult.NotFound -> result.message
                        is GeolocatorResult.PermissionError -> result.message
                        is GeolocatorResult.GeolocationFailed -> result.message
                        else -> "Unknown location error"
                    }
                    updateState { it.copy(autoSearchedLocationError = errorMessage) }
                }

                is GeolocatorResult.Success -> {
                    val coordinates = result.data.coordinates
                    //TODO: get the precise address
                    val locality = MobileGeocoder().placeOrNull(coordinates)?.country.orEmpty()
                    updateState {
                        it.copy(
                            address = locality,
                            latitude = coordinates.latitude.toString(),
                            longitude = coordinates.longitude.toString()
                        )
                    }
                }
            }
        }
    }

    private suspend fun onSearchQueryChanged() {
        val currentState = _state.replayCache.firstOrNull() ?: LocationScreenState()
        updateState { it.copy(searchQuery = currentState.searchQuery) }
    }

    private fun onSearch() {
        viewModelScope.launch {
            val currentState = _state.replayCache.firstOrNull() ?: LocationScreenState()
            autocomplete.search(query = currentState.searchQuery).getOrNull()?.let { results ->
                updateState { it.copy(places = results.toList()) }
            }
            updateState { it.copy(isExpanded = true) }
        }
    }

    private suspend fun onPlaceSelected() {
        val currentState = _state.replayCache.firstOrNull() ?: LocationScreenState()
        updateState { it.copy(selectedPlace = currentState.selectedPlace) }
        updateState { it.copy(isExpanded = false) }
    }

    private suspend fun onDropdownDismissRequest() {
        updateState { it.copy(isExpanded = false) }
    }

    private suspend fun validateForm() {
        val currentState = _state.replayCache.firstOrNull() ?: LocationScreenState()
        val validationResult = validateLocationForm(currentState.address)

        val locationPayloadModel = LocationPayloadModel(
            latitude = currentState.latitude,
            longitude = currentState.longitude,
            address = currentState.address,
            email = currentState.email
        )
        if (validationResult.isAddressValid && currentState.isDeviceConnectedToInternet) {
            saveLocation(locationPayloadModel = locationPayloadModel)
        } else {
            updateState {
                it.copy(
                    addressError = validationResult.addressError,
                    noInternetConnection = false
                )
            }
        }
    }

    private fun saveLocation(locationPayloadModel: LocationPayloadModel) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        locationRepository.uploadUserLocation(locationPayloadModel = locationPayloadModel)
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        responseSuccess = true,
                        responseFailed = false,
                        locationResponseModel = response
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

    private suspend fun updateState(transform: (LocationScreenState) -> LocationScreenState) {
        val currentState = _state.replayCache.firstOrNull() ?: LocationScreenState()
        val newState = transform(currentState)
        _state.emit(newState)
    }
}