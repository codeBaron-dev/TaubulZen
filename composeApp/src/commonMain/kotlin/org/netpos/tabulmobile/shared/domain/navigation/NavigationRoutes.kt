package org.netpos.tabulmobile.shared.domain.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoutes {

    @Serializable
    data object Splash : NavigationRoutes

    @Serializable
    data object Onboarding : NavigationRoutes

    @Serializable
    data object Login : NavigationRoutes

    @Serializable
    data object Home : NavigationRoutes

    @Serializable
    data object Register : NavigationRoutes

    @Serializable
    data object ForgotPassword : NavigationRoutes

    @Serializable
    data object OtpVerification : NavigationRoutes

    @Serializable
    data object CreateNewPassword : NavigationRoutes

    @Serializable
    data object Location : NavigationRoutes

    @Serializable
    data object NavigationRoot : NavigationRoutes

    @Serializable
    data object Basket : NavigationRoutes

    @Serializable
    data object Favourite : NavigationRoutes

    @Serializable
    data object Account : NavigationRoutes

}