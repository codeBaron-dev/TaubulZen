package org.netpos.tabulmobile.shared.domain.tabul_internet_configurations

sealed class ConnectivityType {
    data object WIFI : ConnectivityType()
    data object CELLULAR : ConnectivityType()
    data object ETHERNET : ConnectivityType()
    data object NONE : ConnectivityType()
}