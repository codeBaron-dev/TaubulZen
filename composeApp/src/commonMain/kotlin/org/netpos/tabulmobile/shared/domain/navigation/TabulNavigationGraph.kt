package org.netpos.tabulmobile.shared.domain.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.netpos.tabulmobile.customer.presentation.login.ui.LoginScreenRoot
import org.netpos.tabulmobile.customer.presentation.login.view_model.LoginScreenViewModel
import org.netpos.tabulmobile.customer.presentation.onboard.ui.OnboardScreenRoot
import org.netpos.tabulmobile.customer.presentation.onboard.viewmodel.OnboardScreenViewModel
import org.netpos.tabulmobile.customer.presentation.register.ui.RegisterScreenRoot
import org.netpos.tabulmobile.customer.presentation.register.view_model.RegistrationScreenViewModel
import org.netpos.tabulmobile.customer.presentation.splash.ui.SplashScreenRoot
import org.netpos.tabulmobile.customer.presentation.splash.view_model.SplashScreenViewModel
import org.stakeny.stakeny.shared.domain.navigation.NavigationRoutes

@Composable
fun TabulNavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoutes.Splash) {
        composable<NavigationRoutes.Splash>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val splashScreenViewModel: SplashScreenViewModel = koinViewModel()
            SplashScreenRoot(navController = navController, splashScreenViewModel = splashScreenViewModel)
        }
        composable<NavigationRoutes.Onboarding>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val onboardScreenViewModel: OnboardScreenViewModel = koinViewModel()
            OnboardScreenRoot(navController = navController, onboardScreenViewModel = onboardScreenViewModel)
        }
        composable<NavigationRoutes.Login>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val loginScreenViewModel: LoginScreenViewModel = koinViewModel()
            LoginScreenRoot(navController = navController, loginScreenViewModel = loginScreenViewModel)
        }
        composable<NavigationRoutes.Register>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val registerScreenViewModel: RegistrationScreenViewModel = koinViewModel()
            RegisterScreenRoot(navController = navController, registerScreenViewModel = registerScreenViewModel)
        }
    }
}