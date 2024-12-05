package org.netpos.tabulmobile.customer.domain
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.netpos.tabulmobile.shared.domain.tabul_internet_configurations.ConnectivityChecker
import org.netpos.tabulmobile.shared.domain.tabul_internet_configurations.ConnectivityState
import org.netpos.tabulmobile.shared.domain.tabul_internet_configurations.ConnectivityType
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithName
import platform.SystemConfiguration.SCNetworkReachabilityFlagsVar
import platform.SystemConfiguration.SCNetworkReachabilityGetFlags
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsConnectionRequired
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsIsWWAN
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsReachable

class TabulIOSConnectivityChecker : ConnectivityChecker {
    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getConnectivityState(): ConnectivityState {
        return withContext(Dispatchers.Default) {
            val reachability = SCNetworkReachabilityCreateWithName(null, "google.com")
            val flags = memScoped {
                val flagsVar = alloc<SCNetworkReachabilityFlagsVar>()
                SCNetworkReachabilityGetFlags(reachability, flagsVar.ptr)
                flagsVar.value
            }

            val isConnected =
                (flags.toInt() and kSCNetworkReachabilityFlagsReachable.toInt()) != 0 &&
                        (flags.toInt() and kSCNetworkReachabilityFlagsConnectionRequired.toInt()) == 0

            val type = when {
                (flags.toInt() and kSCNetworkReachabilityFlagsIsWWAN.toInt()) != 0 -> ConnectivityType.CELLULAR
                isConnected -> ConnectivityType.WIFI // Simplified, as checking for Ethernet is more complex
                else -> ConnectivityType.NONE
            }

            ConnectivityState(isConnected, type)
        }
    }
}