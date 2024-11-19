package org.netpos.tabulmobile.merchant.presentation.splash.viewmodel

sealed class SplashScreenIntent {
    data object NavigateToOnboarding : SplashScreenIntent()
    data object NavigateToLogin : SplashScreenIntent()
    data class NavigateToHome(val rememberMe: Boolean) : SplashScreenIntent()
}