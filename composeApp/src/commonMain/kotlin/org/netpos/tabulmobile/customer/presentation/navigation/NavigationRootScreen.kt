package org.netpos.tabulmobile.customer.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import org.netpos.tabulmobile.customer.presentation.home.ui.HomeScreenRoot
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.domain.navigation.TabulBottomNavigationBar
import org.netpos.tabulmobile.shared.domain.navigation.TabulNavigationItems

@Composable
fun NavigationRootScreen(navController: NavHostController) {

    var currentRoute by remember { mutableStateOf(TabulNavigationItems.Home.route) }

    TabulBottomNavigationBar(
        currentRoute = currentRoute,
        onItemSelected = { selectedItem ->
            currentRoute = selectedItem.route
        },
        content = {
            Box(
                content = {
                    when (currentRoute) {
                        TabulNavigationItems.Home.route -> {
                            HomeScreenRoot()
                        }

                        NavigationRoutes.Account -> {}
                        NavigationRoutes.Basket -> {}
                        NavigationRoutes.Favourite -> {}
                        else -> {}
                    }
                }
            )
        }
    )
}