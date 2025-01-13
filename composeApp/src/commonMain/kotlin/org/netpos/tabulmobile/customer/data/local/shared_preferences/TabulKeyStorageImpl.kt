package org.netpos.tabulmobile.customer.data.local.shared_preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.netpos.tabulmobile.customer.data.models.home_screen.response.RestaurantInformation
import org.netpos.tabulmobile.customer.data.models.location.response.saved.Data
import org.netpos.tabulmobile.customer.data.models.login.remote.login.response.User

@OptIn(ExperimentalSerializationApi::class)
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

    override var restaurantInformation: RestaurantInformation?
        get() = try {
            settings.decodeValueOrNull(
                RestaurantInformation.serializer(),
                StorageKeys.RESTAURANT_INFORMATION_KEY.key
            )
        } catch (e: Exception) {
            null
        }
        set(value) {
            if (value != null) {
                settings.encodeValue(
                    RestaurantInformation.serializer(),
                    StorageKeys.RESTAURANT_INFORMATION_KEY.key,
                    value
                )
            } else {
                settings.remove(StorageKeys.RESTAURANT_INFORMATION_KEY.key)
            }
        }

    override var userLoginInfo: User?
        get() = try {
            settings.decodeValueOrNull(User.serializer(), StorageKeys.USER_LOGIN_INFO_KEY.key)
        } catch (e: Exception) {
            null
        }
        set(value) {
            if (value != null) {
                settings.encodeValue(User.serializer(), StorageKeys.USER_LOGIN_INFO_KEY.key, value)
            } else {
                settings.remove(StorageKeys.USER_LOGIN_INFO_KEY.key)
            }
        }

    override var selectedSavedLocation: Data?
        get() = try {
            settings.decodeValueOrNull(
                Data.serializer(),
                StorageKeys.SELECTED_SAVED_LOCATION_KEY.key
            )
        } catch (e: Exception) {
            null
        }
        set(value) {
            if (value != null) {
                settings.encodeValue(
                    Data.serializer(),
                    StorageKeys.SELECTED_SAVED_LOCATION_KEY.key,
                    value
                )
            } else {
                settings.remove(StorageKeys.SELECTED_SAVED_LOCATION_KEY.key)
            }
        }

    override fun clearKeyStorage() {
        settings.clear()
    }
}