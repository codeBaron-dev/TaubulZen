@file:OptIn(ExperimentalMaterial3Api::class)

package org.netpos.tabulmobile.customer.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.LocationSearching
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.ConnectivityCheckerProvider
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorage
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorageImpl
import org.netpos.tabulmobile.customer.presentation.basket.ui.BasketScreenRoot
import org.netpos.tabulmobile.customer.presentation.basket.view_model.BasketScreenViewModel
import org.netpos.tabulmobile.customer.presentation.home.ui.HomeScreenRoot
import org.netpos.tabulmobile.customer.presentation.home.view_model.HomeScreenViewModel
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.domain.navigation.TabulBottomNavigationBar
import org.netpos.tabulmobile.shared.domain.navigation.TabulNavigationItems
import org.netpos.tabulmobile.shared.presentation.location.view_model.LocationScreenState
import org.netpos.tabulmobile.shared.presentation.location.view_model.LocationScreenViewModel
import org.netpos.tabulmobile.shared.presentation.utils.EmptyUiState
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.empty_location_history_text
import tabulmobile.composeapp.generated.resources.empty_screen_state
import tabulmobile.composeapp.generated.resources.enter_new_address_text
import tabulmobile.composeapp.generated.resources.recent_address_text
import tabulmobile.composeapp.generated.resources.update_location_text

@Composable
fun NavigationRootScreen(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    locationScreenViewModel: LocationScreenViewModel,
    basketScreenViewModel: BasketScreenViewModel
) {

    val locationScreenViewModelState by locationScreenViewModel.state.collectAsState(initial = LocationScreenState())
    var currentRoute by remember { mutableStateOf(TabulNavigationItems.Home.route) }
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val isSheetVisible = sheetState.currentValue != ModalBottomSheetValue.Hidden
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val checker = ConnectivityCheckerProvider.getConnectivityChecker()
    var isDeviceConnectedToInternet by remember { mutableStateOf(false) }
    val keyValueStorage: TabulKeyStorage = TabulKeyStorageImpl()

    LaunchedEffect(key1 = Unit) {
        launch { isDeviceConnectedToInternet = checker.getConnectivityState().isConnected }
        launch { locationScreenViewModel.getSavedLocation(email = keyValueStorage.userLoginInfo?.email.toString()) }
    }

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
                        keyValueStorage = keyValueStorage,
                        sheetState = sheetState,
                        locationScreenViewModelState
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
                                NavigationRoutes.Basket -> {
                                    BasketScreenRoot(
                                        navController = navController,
                                        basketScreenViewModel = basketScreenViewModel
                                    )
                                }
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

@Composable
fun SharedLocationBottomSheet(
    keyValueStorage: TabulKeyStorage,
    sheetState: ModalBottomSheetState,
    locationScreenViewModelState: LocationScreenState,
) {
    val coroutineScope = rememberCoroutineScope()
    var showToast by remember { mutableStateOf(false) }

    Surface(
        content = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp), // Removed unnecessary status bar padding
                content = {
                    if (locationScreenViewModelState.savedUserLocationResponse?.data.isNullOrEmpty()) {
                        /*coroutineScope.launch {
                            showToast = true
                            if (showToast) {
                                showToast(message = locationScreenViewModelState.errorMessage.toString())
                                showToast = false
                            }
                        }*/
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                            content = {
                                EmptyUiState(
                                    message = stringResource(Res.string.empty_location_history_text),
                                    painter = Res.drawable.empty_screen_state
                                )
                            }
                        )
                    } else {

                        // Top Row with close button and title
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            content = {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            sheetState.hide()
                                        }
                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                )
                                Text(
                                    text = stringResource(Res.string.update_location_text),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        // OutlinedTextField for email
                        OutlinedTextField(
                            value = "",
                            onValueChange = { /* Handle text input */ },
                            label = {
                                Text(
                                    text = stringResource(Res.string.enter_new_address_text),
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                        fontSize = 13.sp
                                    )
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.LocationSearching,
                                    contentDescription = null
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedTextColor = Color.LightGray,
                                errorTextColor = Color.Red,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.onSurface,
                                errorCursorColor = Color.Red,
                                selectionColors = LocalTextSelectionColors.current,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                errorBorderColor = Color.Red,
                                focusedBorderColor = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(Res.string.recent_address_text),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                fontSize = 16.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn(
                            content = {
                                locationScreenViewModelState.savedUserLocationResponse?.data?.let { locations ->
                                    locations.forEachIndexed { index, data ->
                                        items(locations) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.fillMaxWidth().padding(5.dp),
                                                content = {
                                                    Icon(
                                                        imageVector = Icons.Outlined.MyLocation,
                                                        contentDescription = null
                                                    )
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    TextButton(
                                                        onClick = {
                                                            coroutineScope.launch {
                                                                keyValueStorage.selectedSavedLocation =
                                                                    data
                                                                sheetState.hide()
                                                            }
                                                        },
                                                        content = {
                                                            Text(
                                                                text = data?.address.toString(),
                                                                style = MaterialTheme.typography.labelSmall.copy(
                                                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                                                    fontSize = 13.sp
                                                                )
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            )
        }
    )
}