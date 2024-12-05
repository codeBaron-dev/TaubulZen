package org.netpos.tabulmobile.customer.presentation.splash.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes

class SplashScreenViewModel: ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun sendIntent(intent: SplashScreenIntent) {
        viewModelScope.launch {
            when (intent) {
                is SplashScreenIntent.NavigateToOnboarding -> {
                    //_navigationEvent.emit(value = NavigationRoutes.Onboarding)
                    _navigationEvent.emit(value = NavigationRoutes.Location)
                }

                SplashScreenIntent.NavigateToHome -> {
                    _navigationEvent.emit(value = NavigationRoutes.Home)
                }

                SplashScreenIntent.NavigateToLogin -> {
                    _navigationEvent.emit(value = NavigationRoutes.Login)
                }
            }
        }
    }
}