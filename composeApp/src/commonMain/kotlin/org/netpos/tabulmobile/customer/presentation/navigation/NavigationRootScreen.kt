@file:OptIn(ExperimentalMaterial3Api::class)

package org.netpos.tabulmobile.customer.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.netpos.tabulmobile.customer.presentation.home.ui.HomeScreenRoot
import org.netpos.tabulmobile.customer.presentation.home.view_model.HomeScreenViewModel
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.domain.navigation.TabulBottomNavigationBar
import org.netpos.tabulmobile.shared.domain.navigation.TabulNavigationItems
import org.netpos.tabulmobile.shared.presentation.utils.SharedLocationBottomSheet

@Composable
fun NavigationRootScreen(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel
) {

    var currentRoute by remember { mutableStateOf(TabulNavigationItems.Home.route) }
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val isSheetVisible = sheetState.currentValue != ModalBottomSheetValue.Hidden
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    TabulBottomNavigationBar(
        currentRoute = currentRoute,
        onItemSelected = { selectedItem ->
            currentRoute = selectedItem.route
        },
        content = {
            ModalBottomSheetLayout(
                sheetState = sheetState,
                sheetContent = {
                    SharedLocationBottomSheet(
                        sheetState = sheetState
                    )
                },
                sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                scrimColor = Color.Gray.copy(alpha = 0.5f),
                content = {
                    Box(
                        content = {
                            when (currentRoute) {
                                TabulNavigationItems.Home.route -> {
                                    HomeScreenRoot(
                                        sheetState = sheetState,
                                        homeScreenViewModel = homeScreenViewModel,
                                        navController = navController
                                    )
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
    )
}