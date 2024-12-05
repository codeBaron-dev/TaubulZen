package org.netpos.tabulmobile.shared.presentation.location.view_model

import dev.jordond.compass.Place

sealed class LocationScreenIntent {
    data class AddressChanged(val address: String): LocationScreenIntent()
    data class SearchedAddressChanged(val searchedAddress: List<String>): LocationScreenIntent()
    data class SearchActionClick(val isDeviceConnectedToInternet: Boolean) : LocationScreenIntent()
    data class SaveLocationActionClick(val email: String, val isDeviceConnectedToInternet: Boolean) : LocationScreenIntent()
    data object AutoFetchLocation : LocationScreenIntent()
    data object OnSearchQueryAction : LocationScreenIntent()
    data class OnSearchAction(val query: String) : LocationScreenIntent()
    data class OnPlaceSelected(val place: Place) : LocationScreenIntent()
    data object OnDropdownDismissRequest: LocationScreenIntent()
    data object HomeActionClick: LocationScreenIntent()
}