package org.netpos.tabulmobile.merchant.data.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.netpos.tabulmobile.merchant.data.remote.HttpClientFactory
import org.netpos.tabulmobile.merchant.presentation.onboard.viewmodel.OnboardScreenViewModel
import org.netpos.tabulmobile.merchant.presentation.splash.viewmodel.SplashScreenViewModel

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    viewModel { SplashScreenViewModel() }
    viewModel { OnboardScreenViewModel() }
}