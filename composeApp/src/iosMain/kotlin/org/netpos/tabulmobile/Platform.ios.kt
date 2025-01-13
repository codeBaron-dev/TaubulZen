package org.netpos.tabulmobile

import org.netpos.tabulmobile.customer.domain.TabulIOSConnectivityChecker
import org.netpos.tabulmobile.shared.domain.tabul_internet_configurations.ConnectivityChecker
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_SEC
import platform.darwin.dispatch_after
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time

class IOSPlatform: Platform {
    override val name: String = "ios"
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun showToast(message: String) {
    val alert = UIAlertController.alertControllerWithTitle(
        title = null,
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )

    // Dismiss the alert automatically after 2 seconds
    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootViewController?.presentViewController(alert, animated = true) {
        dispatch_after(
            dispatch_time(DISPATCH_TIME_NOW, 2 * NSEC_PER_SEC.toLong()),
            dispatch_get_main_queue()
        ) {
            alert.dismissViewControllerAnimated(true, null)
        }
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object ConnectivityCheckerProvider {
    actual fun getConnectivityChecker(): ConnectivityChecker {
        return TabulIOSConnectivityChecker()
    }
}