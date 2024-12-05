package org.netpos.tabulmobile

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.netpos.tabulmobile.customer.data.di.initKoin

class TabulApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ConnectivityCheckerProvider.context = this
        initKoin {
            androidContext(this@TabulApplication)
        }
    }
}