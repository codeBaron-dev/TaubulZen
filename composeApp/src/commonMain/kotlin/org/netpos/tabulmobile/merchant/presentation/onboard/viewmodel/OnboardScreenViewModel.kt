package org.netpos.tabulmobile.merchant.presentation.onboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.stakeny.stakeny.shared.domain.navigation.NavigationRoutes

class OnboardScreenViewModel : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavigationRoutes>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun sendIntent(intent: OnboardScreenIntent) {
        viewModelScope.launch {
            when (intent) {
                OnboardScreenIntent.LoginActionClick -> {
                    _navigationEvent.emit(value = NavigationRoutes.Login)
                }

                OnboardScreenIntent.NextActionClick -> {
                    //TODO: change this route
                    _navigationEvent.emit(value = NavigationRoutes.Login)
                }

                OnboardScreenIntent.RegisterActionClick -> {
                    _navigationEvent.emit(value = NavigationRoutes.Register)
                }

                OnboardScreenIntent.SkipActionClick -> {
                    //TODO: change this route
                    _navigationEvent.emit(value = NavigationRoutes.Login)
                }
            }
        }
    }
}