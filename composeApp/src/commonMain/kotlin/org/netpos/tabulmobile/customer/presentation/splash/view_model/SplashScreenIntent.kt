package org.netpos.tabulmobile.customer.presentation.splash.view_model

sealed class SplashScreenIntent {
    data object NavigateToOnboarding : SplashScreenIntent()
    data object NavigateToLogin : SplashScreenIntent()
    data class NavigateToHome(val rememberMe: Boolean) : SplashScreenIntent()
}