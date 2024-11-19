package org.stakeny.stakeny.shared.domain.navigation

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
}