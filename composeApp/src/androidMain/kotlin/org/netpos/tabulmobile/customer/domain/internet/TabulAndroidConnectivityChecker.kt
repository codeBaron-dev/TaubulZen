package org.netpos.tabulmobile.customer.domain.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.netpos.tabulmobile.shared.domain.tabul_internet_configurations.ConnectivityChecker
import org.netpos.tabulmobile.shared.domain.tabul_internet_configurations.ConnectivityState
import org.netpos.tabulmobile.shared.domain.tabul_internet_configurations.ConnectivityType

class TabulAndroidConnectivityChecker(private val context: Context) : ConnectivityChecker {
    override suspend fun getConnectivityState(): ConnectivityState {
        return withContext(Dispatchers.IO) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            val isConnected =
                capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

            val type = when {
                capabilities == null -> ConnectivityType.NONE
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectivityType.WIFI
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectivityType.CELLULAR
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectivityType.ETHERNET
                else -> ConnectivityType.NONE
            }
            ConnectivityState(isConnected, type)
        }
    }
}