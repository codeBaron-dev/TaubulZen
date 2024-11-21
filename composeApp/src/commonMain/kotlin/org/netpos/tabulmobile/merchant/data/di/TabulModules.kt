package org.netpos.tabulmobile.merchant.data.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.netpos.tabulmobile.merchant.data.remote.HttpClientFactory
import org.netpos.tabulmobile.merchant.data.remote.network.KtorRemoteDataSource
import org.netpos.tabulmobile.merchant.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.merchant.data.remote.repository.LoginRepository
import org.netpos.tabulmobile.merchant.data.remote.repository.login.LoginRepositoryInterface
import org.netpos.tabulmobile.merchant.presentation.login.viewmodels.LoginScreenViewModel
import org.netpos.tabulmobile.merchant.presentation.onboard.viewmodel.OnboardScreenViewModel
import org.netpos.tabulmobile.merchant.presentation.splash.viewmodel.SplashScreenViewModel

expect val platformModule: Module

val sharedModule = module {

    viewModel { SplashScreenViewModel() }
    viewModel { OnboardScreenViewModel() }
    viewModel { LoginScreenViewModel(loginRepository = get()) }

    single { HttpClientFactory.create(engine = get()) }
    singleOf(::KtorRemoteDataSource).bind<RemoteDataSource>()
    singleOf(::LoginRepository).bind<LoginRepositoryInterface>()
}