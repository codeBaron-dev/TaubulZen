package org.netpos.tabulmobile.shared.domain.tabul_internet_configurations

interface ConnectivityChecker {
    suspend fun getConnectivityState(): ConnectivityState
}

//Use to check connection
//val checker = ConnectivityCheckerProvider.getConnectivityChecker()
//val connectivityState = checker.getConnectivityState()