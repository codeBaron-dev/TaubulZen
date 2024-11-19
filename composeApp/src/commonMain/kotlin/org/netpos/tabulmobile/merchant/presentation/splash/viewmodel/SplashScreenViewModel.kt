package org.netpos.tabulmobile.merchant.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.stakeny.stakeny.shared.domain.navigation.NavigationRoutes

class SplashScreenViewModel: ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun sendIntent(intent: SplashScreenIntent) {
        viewModelScope.launch {
            when (intent) {
                is SplashScreenIntent.NavigateToOnboarding -> {
                    _navigationEvent.emit(value = NavigationRoutes.Onboarding)
                }

                is SplashScreenIntent.NavigateToHome -> {
                    _navigationEvent.emit(value = NavigationRoutes.Home)
                }

                SplashScreenIntent.NavigateToLogin -> {
                    _navigationEvent.emit(value = NavigationRoutes.Login)
                }
            }
        }
    }
}