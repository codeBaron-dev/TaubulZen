package org.netpos.tabulmobile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import org.netpos.tabulmobile.customer.domain.internet.TabulAndroidConnectivityChecker
import org.netpos.tabulmobile.shared.domain.tabul_internet_configurations.ConnectivityChecker

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

private lateinit var appContext: Context

fun initToastContext(context: Context) {
    appContext = context
}

actual fun showToast(message: String) {
    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@SuppressLint("StaticFieldLeak")
actual object ConnectivityCheckerProvider {
    lateinit var context: Context

    actual fun getConnectivityChecker(): ConnectivityChecker {
        return TabulAndroidConnectivityChecker(context)
    }
}