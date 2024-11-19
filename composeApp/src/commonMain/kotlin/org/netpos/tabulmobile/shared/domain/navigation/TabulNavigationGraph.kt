package org.netpos.tabulmobile.shared.domain.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.netpos.tabulmobile.merchant.presentation.login.ui.LoginScreenRoot
import org.netpos.tabulmobile.merchant.presentation.onboard.ui.OnboardScreenRoot
import org.netpos.tabulmobile.merchant.presentation.onboard.viewmodel.OnboardScreenViewModel
import org.netpos.tabulmobile.merchant.presentation.register.ui.RegisterScreenRoot
import org.netpos.tabulmobile.merchant.presentation.splash.ui.SplashScreenRoot
import org.netpos.tabulmobile.merchant.presentation.splash.viewmodel.SplashScreenViewModel
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
            SplashScreenRoot(navController, splashScreenViewModel)
        }
        composable<NavigationRoutes.Onboarding>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val onboardScreenViewModel: OnboardScreenViewModel = koinViewModel()
            OnboardScreenRoot(navController, onboardScreenViewModel)
        }
        composable<NavigationRoutes.Login>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            LoginScreenRoot()
        }
        composable<NavigationRoutes.Register>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            RegisterScreenRoot()
        }
    }
}