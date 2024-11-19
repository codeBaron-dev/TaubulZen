package org.netpos.tabulmobile

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.netpos.tabulmobile.merchant.data.di.initKoin

class TabulApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TabulApplication)
        }
    }
}