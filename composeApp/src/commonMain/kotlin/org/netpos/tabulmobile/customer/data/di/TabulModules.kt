package org.netpos.tabulmobile.customer.data.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.netpos.tabulmobile.customer.data.remote.HttpClientFactory
import org.netpos.tabulmobile.customer.data.remote.network.KtorRemoteDataSource
import org.netpos.tabulmobile.customer.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.customer.data.remote.repository.login.LoginRepository
import org.netpos.tabulmobile.customer.data.remote.repository.login.LoginRepositoryInterface
import org.netpos.tabulmobile.customer.data.remote.repository.register.RegistrationRepositoryInterface
import org.netpos.tabulmobile.customer.data.remote.repository.register.RegistrationRepository
import org.netpos.tabulmobile.customer.presentation.login.view_model.LoginScreenViewModel
import org.netpos.tabulmobile.customer.presentation.onboard.viewmodel.OnboardScreenViewModel
import org.netpos.tabulmobile.customer.presentation.register.view_model.RegistrationScreenViewModel
import org.netpos.tabulmobile.customer.presentation.splash.view_model.SplashScreenViewModel

expect val platformModule: Module

val sharedModule = module {

    viewModel { SplashScreenViewModel() }
    viewModel { OnboardScreenViewModel() }
    viewModel { LoginScreenViewModel(loginRepository = get()) }
    viewModel { RegistrationScreenViewModel(registrationRepository = get()) }

    single { HttpClientFactory.create(engine = get()) }
    singleOf(::KtorRemoteDataSource).bind<RemoteDataSource>()
    singleOf(::LoginRepository).bind<LoginRepositoryInterface>()
    singleOf(::RegistrationRepository).bind<RegistrationRepositoryInterface>()
}