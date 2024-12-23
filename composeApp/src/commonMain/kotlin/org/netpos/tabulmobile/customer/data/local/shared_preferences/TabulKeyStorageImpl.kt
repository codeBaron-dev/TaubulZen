package org.netpos.tabulmobile.customer.data.local.shared_preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TabulKeyStorageImpl : TabulKeyStorage {

    private val settings: Settings by lazy { Settings() }
    private val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    override var rememberMe: Boolean?
        get() = settings.getBooleanOrNull(StorageKeys.REMEMBER_ME_KEY.key)
        set(value) {
            if (value != null) {
                settings.putBoolean(StorageKeys.REMEMBER_ME_KEY.key, value)
            } else {
                settings.remove(StorageKeys.REMEMBER_ME_KEY.key)
            }
        }

    override var email: String?
        get() = settings.getStringOrNull(StorageKeys.EMAIL_KEY.key)
        set(value) {
            if (value != null) {
                settings.putString(StorageKeys.EMAIL_KEY.key, value)
            } else {
                settings.remove(StorageKeys.EMAIL_KEY.key)
            }
        }

    override var token: String?
        get() = settings.getStringOrNull(StorageKeys.TOKEN_KEY.key)
        set(value) {
            if (value != null) {
                settings.putString(StorageKeys.TOKEN_KEY.key, value)
            } else {
                settings.remove(StorageKeys.TOKEN_KEY.key)
            }
        }

    override var searchHistory: List<String>?
        get() = try {
            settings.getStringOrNull(StorageKeys.SEARCH_HISTORY_KEY.key)?.let { jsonString ->
                json.decodeFromString<List<String>>(jsonString)
            }
        } catch (e: Exception) {
            null
        }
        set(value) {
            if (value != null) {
                val jsonString = json.encodeToString(value)
                settings.putString(StorageKeys.SEARCH_HISTORY_KEY.key, jsonString)
            } else {
                settings.remove(StorageKeys.SEARCH_HISTORY_KEY.key)
            }
        }

    override fun clearKeyStorage() {
        settings.clear()
    }
}