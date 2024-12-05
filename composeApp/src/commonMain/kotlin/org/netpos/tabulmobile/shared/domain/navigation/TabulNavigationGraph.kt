package org.netpos.tabulmobile.shared.domain.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.netpos.tabulmobile.customer.presentation.home.ui.HomeScreenRoot
import org.netpos.tabulmobile.customer.presentation.login.ui.LoginScreenRoot
import org.netpos.tabulmobile.customer.presentation.login.view_model.LoginScreenViewModel
import org.netpos.tabulmobile.customer.presentation.onboard.ui.OnboardScreenRoot
import org.netpos.tabulmobile.customer.presentation.onboard.viewmodel.OnboardScreenViewModel
import org.netpos.tabulmobile.customer.presentation.otp.view_model.OtpVerificationViewModel
import org.netpos.tabulmobile.customer.presentation.password_reset.create_new_password.ui.CreateNewPasswordRoot
import org.netpos.tabulmobile.customer.presentation.password_reset.create_new_password.view_model.CreateNewPasswordScreenViewModel
import org.netpos.tabulmobile.customer.presentation.password_reset.ui.PasswordResetScreenRoot
import org.netpos.tabulmobile.customer.presentation.password_reset.view_model.PasswordResetViewModel
import org.netpos.tabulmobile.customer.presentation.register.ui.RegisterScreenRoot
import org.netpos.tabulmobile.customer.presentation.register.view_model.RegistrationScreenViewModel
import org.netpos.tabulmobile.customer.presentation.splash.ui.SplashScreenRoot
import org.netpos.tabulmobile.customer.presentation.splash.view_model.SplashScreenViewModel
import org.netpos.tabulmobile.shared.presentation.location.ui.LocationScreenRoot
import org.netpos.tabulmobile.shared.presentation.location.view_model.LocationScreenViewModel
import org.stakeny.stakeny.onboard.presentation.otp.ui.OtpVerificationScreenRoot

@Composable
fun TabulNavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoutes.Splash) {
        composable<NavigationRoutes.Splash>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val splashScreenViewModel: SplashScreenViewModel = koinViewModel()
            SplashScreenRoot(
                navController = navController,
                splashScreenViewModel = splashScreenViewModel
            )
        }
        composable<NavigationRoutes.Onboarding>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val onboardScreenViewModel: OnboardScreenViewModel = koinViewModel()
            OnboardScreenRoot(
                navController = navController,
                onboardScreenViewModel = onboardScreenViewModel
            )
        }
        composable<NavigationRoutes.Login>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val loginScreenViewModel: LoginScreenViewModel = koinViewModel()
            LoginScreenRoot(
                navController = navController,
                loginScreenViewModel = loginScreenViewModel
            )
        }
        composable<NavigationRoutes.Register>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val registerScreenViewModel: RegistrationScreenViewModel = koinViewModel()
            RegisterScreenRoot(
                navController = navController,
                registerScreenViewModel = registerScreenViewModel
            )
        }
        composable<NavigationRoutes.ForgotPassword>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val passwordResetViewModel: PasswordResetViewModel = koinViewModel()
            PasswordResetScreenRoot(
                navController = navController,
                passwordResetViewModel = passwordResetViewModel
            )
        }
        composable<NavigationRoutes.OtpVerification>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val otpVerificationViewModel: OtpVerificationViewModel = koinViewModel()
            OtpVerificationScreenRoot(
                navController = navController,
                otpVerificationViewModel = otpVerificationViewModel
            )
        }
        composable<NavigationRoutes.CreateNewPassword>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val createNewPasswordViewModel: CreateNewPasswordScreenViewModel = koinViewModel()
            CreateNewPasswordRoot(
                navController = navController,
                createNewPasswordViewModel = createNewPasswordViewModel
            )
        }
        composable<NavigationRoutes.Location>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val locationScreenViewModel: LocationScreenViewModel = koinViewModel()
            LocationScreenRoot(
                navController = navController,
                locationScreenViewModel = locationScreenViewModel
            )
        }
        composable<NavigationRoutes.Home>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            HomeScreenRoot()
        }
    }
}