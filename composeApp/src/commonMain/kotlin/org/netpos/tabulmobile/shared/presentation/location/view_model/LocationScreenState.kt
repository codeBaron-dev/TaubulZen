package org.netpos.tabulmobile.shared.presentation.location.view_model

import dev.jordond.compass.Place
import org.netpos.tabulmobile.customer.data.models.location.response.LocationResponseModel
import org.netpos.tabulmobile.customer.data.models.location.response.saved.SavedUserLocationResponse

data class LocationScreenState (
    val searchedAddress: List<String> = emptyList(),
    val latitude: String = "",
    val longitude: String = "",
    val latitudeError: String? = null,
    val longitudeError: String? = null,
    val address: String = "",
    val email: String = "",
    val addressError: String? = null,
    val autoCompleteAddress: String = "",
    val autoCompleteAddressError: String? = null,
    val isLoading: Boolean = false,
    val isDeviceConnectedToInternet: Boolean = false,
    val noInternetConnection: Boolean = false,
    val responseSuccess: Boolean = false,
    val responseFailed: Boolean = false,
    val errorMessage: String? = null,
    val locationResponseModel: LocationResponseModel? = null,
    val autoSearchedLocation: String = "",
    val autoSearchedLocationError: String = "",

    val searchQuery: String = "",
    val places: List<Place> = emptyList(),
    val selectedPlace: Place? = null,
    val isExpanded: Boolean = false,
    val savedUserLocationResponse: SavedUserLocationResponse? = null,
)