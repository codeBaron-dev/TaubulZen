package org.netpos.tabulmobile.shared.domain.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.netpos.tabulmobile.customer.presentation.basket.view_model.BasketScreenViewModel
import org.netpos.tabulmobile.customer.presentation.home.view_model.HomeScreenViewModel
import org.netpos.tabulmobile.customer.presentation.login.ui.LoginScreenRoot
import org.netpos.tabulmobile.customer.presentation.login.view_model.LoginScreenViewModel
import org.netpos.tabulmobile.customer.presentation.navigation.NavigationRootScreen
import org.netpos.tabulmobile.customer.presentation.onboard.ui.OnboardScreenRoot
import org.netpos.tabulmobile.customer.presentation.onboard.viewmodel.OnboardScreenViewModel
import org.netpos.tabulmobile.customer.presentation.otp.view_model.OtpVerificationViewModel
import org.netpos.tabulmobile.customer.presentation.password_reset.create_new_password.ui.CreateNewPasswordRoot
import org.netpos.tabulmobile.customer.presentation.password_reset.create_new_password.view_model.CreateNewPasswordScreenViewModel
import org.netpos.tabulmobile.customer.presentation.password_reset.ui.PasswordResetScreenRoot
import org.netpos.tabulmobile.customer.presentation.password_reset.view_model.PasswordResetViewModel
import org.netpos.tabulmobile.customer.presentation.register.ui.RegisterScreenRoot
import org.netpos.tabulmobile.customer.presentation.register.view_model.RegistrationScreenViewModel
import org.netpos.tabulmobile.customer.presentation.restaurant_details.ui.RestaurantScreenRoot
import org.netpos.tabulmobile.customer.presentation.restaurant_details.view_model.RestaurantDetailScreenViewModel
import org.netpos.tabulmobile.customer.presentation.splash.ui.SplashScreenRoot
import org.netpos.tabulmobile.customer.presentation.splash.view_model.SplashScreenViewModel
import org.netpos.tabulmobile.shared.presentation.location.ui.LocationScreenRoot
import org.netpos.tabulmobile.shared.presentation.location.view_model.LocationScreenViewModel
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import org.stakeny.stakeny.onboard.presentation.otp.ui.OtpVerificationScreenRoot
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.Res

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
        composable<NavigationRoutes.NavigationRoot>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val homeScreenViewModel: HomeScreenViewModel = koinViewModel()
            val locationScreenViewModel: LocationScreenViewModel = koinViewModel()
            val basketScreenViewModel: BasketScreenViewModel = koinViewModel()
            NavigationRootScreen(
                navController = navController,
                homeScreenViewModel = homeScreenViewModel,
                locationScreenViewModel = locationScreenViewModel,
                basketScreenViewModel = basketScreenViewModel
            )
        }

        composable<NavigationRoutes.RestaurantDetail>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val restaurantDetailScreenViewModel: RestaurantDetailScreenViewModel = koinViewModel()
            RestaurantScreenRoot(
                navController = navController,
                restaurantDetailScreenViewModel = restaurantDetailScreenViewModel
            )
        }

        /*composable<NavigationRoutes.Basket>(
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() }
        ) {
            val basketScreenViewModel: BasketScreenViewModel = koinViewModel()
            BasketScreenRoot(
                navController = navController,
                basketScreenViewModel = basketScreenViewModel
            )
        }*/
    }
}

@Composable
fun TabulBottomNavigationBar(
    currentRoute: NavigationRoutes,
    onItemSelected: (TabulNavigationItems) -> Unit,
    content: @Composable () -> Unit = {}
) {

    val navSuiteType =
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())

    val navigationItems = listOf(
        TabulNavigationItems.Home,
        TabulNavigationItems.Basket,
        TabulNavigationItems.Favourite,
        TabulNavigationItems.Account
    )

    NavigationSuiteScaffold(
        modifier = Modifier.fillMaxSize(),
        layoutType = navSuiteType,
        navigationSuiteItems = {
            navigationItems.forEach { item ->
                val isSelected = currentRoute == item.route
                item(
                    selected = isSelected,
                    onClick = { onItemSelected(item) },
                    icon = {
                        Icon(
                            imageVector = item.defaultIcon,
                            contentDescription = stringResource(item.title),
                            tint = if (isSelected) {
                                tabulColor
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(item.title),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                color = if (isSelected) {
                                    tabulColor
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )
                        )
                    }
                )
            }
        },
        content = { content() }
    )
}