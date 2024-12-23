package org.netpos.tabulmobile.customer.data.local.shared_preferences

enum class StorageKeys {
    REMEMBER_ME_KEY,
    EMAIL_KEY,
    TOKEN_KEY,
    SEARCH_HISTORY_KEY;

    val key get() = this.name
}