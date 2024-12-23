package org.netpos.tabulmobile.customer.data.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.netpos.tabulmobile.customer.data.remote.HttpClientFactory
import org.netpos.tabulmobile.customer.data.remote.network.KtorRemoteDataSource
import org.netpos.tabulmobile.customer.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.customer.data.remote.repository.home.HomeRepository
import org.netpos.tabulmobile.customer.data.remote.repository.home.HomeRepositoryInterface
import org.netpos.tabulmobile.customer.data.remote.repository.location.LocationRepository
import org.netpos.tabulmobile.customer.data.remote.repository.location.LocationRepositoryInterface
import org.netpos.tabulmobile.customer.data.remote.repository.login.LoginRepository
import org.netpos.tabulmobile.customer.data.remote.repository.login.LoginRepositoryInterface
import org.netpos.tabulmobile.customer.data.remote.repository.otp_verification.OtpVerificationRepository
import org.netpos.tabulmobile.customer.data.remote.repository.otp_verification.OtpVerificationRepositoryInterface
import org.netpos.tabulmobile.customer.data.remote.repository.password_reset.PasswordResetRepository
import org.netpos.tabulmobile.customer.data.remote.repository.password_reset.PasswordResetRepositoryInterface
import org.netpos.tabulmobile.customer.data.remote.repository.register.RegistrationRepository
import org.netpos.tabulmobile.customer.data.remote.repository.register.RegistrationRepositoryInterface
import org.netpos.tabulmobile.customer.presentation.home.view_model.HomeScreenViewModel
import org.netpos.tabulmobile.customer.presentation.login.view_model.LoginScreenViewModel
import org.netpos.tabulmobile.customer.presentation.onboard.viewmodel.OnboardScreenViewModel
import org.netpos.tabulmobile.customer.presentation.otp.view_model.OtpVerificationViewModel
import org.netpos.tabulmobile.customer.presentation.password_reset.view_model.PasswordResetViewModel
import org.netpos.tabulmobile.customer.presentation.register.view_model.RegistrationScreenViewModel
import org.netpos.tabulmobile.customer.presentation.splash.view_model.SplashScreenViewModel
import org.netpos.tabulmobile.shared.presentation.location.view_model.LocationScreenViewModel

expect val platformModule: Module

val sharedModule = module {

    viewModel { SplashScreenViewModel() }
    viewModel { OnboardScreenViewModel() }
    viewModel { LoginScreenViewModel(loginRepository = get()) }
    viewModel { RegistrationScreenViewModel(registrationRepository = get()) }
    viewModel { PasswordResetViewModel(passwordResetRepository = get()) }
    viewModel {
        OtpVerificationViewModel(
            otpVerificationRepository = get(),
            passwordResetRepository = get()
        )
    }
    viewModel { LocationScreenViewModel(locationRepository = get()) }
    viewModel { HomeScreenViewModel(homeScreenRepository = get()) }

    single { HttpClientFactory.create(engine = get()) }
    singleOf(::KtorRemoteDataSource).bind<RemoteDataSource>()
    singleOf(::LoginRepository).bind<LoginRepositoryInterface>()
    singleOf(::RegistrationRepository).bind<RegistrationRepositoryInterface>()
    singleOf(::PasswordResetRepository).bind<PasswordResetRepositoryInterface>()
    singleOf(::OtpVerificationRepository).bind<OtpVerificationRepositoryInterface>()
    singleOf(::LocationRepository).bind<LocationRepositoryInterface>()
    singleOf(::HomeRepository).bind<HomeRepositoryInterface>()
}