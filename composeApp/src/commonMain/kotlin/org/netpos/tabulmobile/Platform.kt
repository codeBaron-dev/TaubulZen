package org.netpos.tabulmobile

import org.netpos.tabulmobile.shared.domain.tabul_internet_configurations.ConnectivityChecker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun showToast(message: String)

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object ConnectivityCheckerProvider {
    fun getConnectivityChecker(): ConnectivityChecker
}