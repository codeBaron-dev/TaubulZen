package org.netpos.tabulmobile.customer.data.local.shared_preferences

import org.netpos.tabulmobile.customer.data.models.home_screen.response.RestaurantInformation
import org.netpos.tabulmobile.customer.data.models.location.response.saved.Data
import org.netpos.tabulmobile.customer.data.models.login.remote.login.response.User

interface TabulKeyStorage {
    var rememberMe: Boolean?
    var email: String?
    var token: String?
    var searchHistory: List<String>?
    var restaurantInformation: RestaurantInformation?
    var userLoginInfo: User?
    var selectedSavedLocation: Data?

    fun clearKeyStorage()
}