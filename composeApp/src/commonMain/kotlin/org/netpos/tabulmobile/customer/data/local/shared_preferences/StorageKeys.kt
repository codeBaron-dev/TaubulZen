package org.netpos.tabulmobile.customer.data.local.shared_preferences

enum class StorageKeys {
    REMEMBER_ME_KEY,
    EMAIL_KEY,
    TOKEN_KEY,
    SEARCH_HISTORY_KEY,
    RESTAURANT_INFORMATION_KEY,
    USER_LOGIN_INFO_KEY,
    SELECTED_SAVED_LOCATION_KEY;

    val key get() = this.name
}