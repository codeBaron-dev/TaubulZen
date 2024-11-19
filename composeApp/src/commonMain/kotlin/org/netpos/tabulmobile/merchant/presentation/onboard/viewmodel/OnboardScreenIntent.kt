package org.netpos.tabulmobile.merchant.presentation.onboard.viewmodel

sealed class OnboardScreenIntent {
    data object SkipActionClick: OnboardScreenIntent()
    data object NextActionClick: OnboardScreenIntent()
    data object LoginActionClick: OnboardScreenIntent()
    data object RegisterActionClick: OnboardScreenIntent()
}