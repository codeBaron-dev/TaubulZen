package org.netpos.tabulmobile.customer.data.local.shared_preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class TabulKeyStorageImpl: TabulKeyStorage {

    private val settings: Settings by lazy { Settings() }
    private val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }

    override var rememberMe: Boolean?
        get() = settings[StorageKeys.REMEMBER_ME_KEY.key]
        set(value) {
            settings[StorageKeys.REMEMBER_ME_KEY.key] = value
        }
    override var email: String?
        get() = settings[StorageKeys.EMAIL_KEY.key]
        set(value) { settings[StorageKeys.EMAIL_KEY.key] = value }

    override fun clearKeyStorage() {
        settings.clear()
    }
}