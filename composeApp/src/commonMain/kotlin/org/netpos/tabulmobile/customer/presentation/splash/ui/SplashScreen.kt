package org.netpos.tabulmobile.customer.presentation.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.netpos.tabulmobile.customer.presentation.splash.view_model.SplashScreenIntent
import org.netpos.tabulmobile.customer.presentation.splash.view_model.SplashScreenViewModel
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.tb_logo
import tabulmobile.composeapp.generated.resources.tb_logo_light

@Composable
fun SplashScreenRoot(
    navController: NavHostController,
    splashScreenViewModel: SplashScreenViewModel
) {

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        splashScreenViewModel.navigationEvent.collect { destination ->
            navController.navigate(route = destination) {
                popUpTo(route = NavigationRoutes.Splash) { inclusive = true }
            }
        }
    }

    SplashScreen(coroutineScope = coroutineScope, splashScreenViewModel = splashScreenViewModel)
}

@Composable
fun SplashScreen(coroutineScope: CoroutineScope, splashScreenViewModel: SplashScreenViewModel) {

    Scaffold(
        content = {
            coroutineScope.launch {
                delay(2000)
                splashScreenViewModel.sendIntent(SplashScreenIntent.NavigateToOnboarding)
            }

            Box(
                modifier = Modifier.padding(it).fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .width(100.dp).height(150.dp),
                    painter = painterResource(if (isSystemInDarkTheme()) Res.drawable.tb_logo_light else Res.drawable.tb_logo),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            }
        }
    )
}