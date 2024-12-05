package org.netpos.tabulmobile.customer.data.local.shared_preferences

interface TabulKeyStorage {
    var rememberMe: Boolean?
    var email: String?

    fun clearKeyStorage()
}