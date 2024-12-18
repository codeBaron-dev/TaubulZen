@file:OptIn(ExperimentalMaterial3Api::class)

package org.netpos.tabulmobile.shared.presentation.location.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationSearching
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.ConnectivityCheckerProvider
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorage
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorageImpl
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.presentation.location.view_model.LocationScreenIntent
import org.netpos.tabulmobile.shared.presentation.location.view_model.LocationScreenState
import org.netpos.tabulmobile.shared.presentation.location.view_model.LocationScreenViewModel
import org.netpos.tabulmobile.shared.presentation.utils.CustomLoadingDialog
import org.netpos.tabulmobile.shared.presentation.utils.TabulButton
import org.netpos.tabulmobile.shared.presentation.utils.TabulTopAppBar
import org.netpos.tabulmobile.showToast
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.enter_location_text
import tabulmobile.composeapp.generated.resources.location_text
import tabulmobile.composeapp.generated.resources.save_location_text
import tabulmobile.composeapp.generated.resources.saving_location_text
import tabulmobile.composeapp.generated.resources.third_pager_sub_heading

@Composable
fun LocationScreenRoot(
    navController: NavHostController, locationScreenViewModel: LocationScreenViewModel
) {
    val locationScreenViewModelState by locationScreenViewModel.state.collectAsState(
        LocationScreenState()
    )

    LocationScreen(
        locationScreenViewModelState, onAction = { intent ->
            locationScreenViewModel.sendIntent(intent)
        }, locationScreenViewModel.navigationEvent, navController = navController
    )
}

@Composable
fun LocationScreen(
    locationScreenViewModelState: LocationScreenState,
    onAction: (LocationScreenIntent) -> Unit,
    navigationEvent: SharedFlow<NavigationRoutes>,
    navController: NavHostController
) {

    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    var showToast by remember { mutableStateOf(false) }
    val checker = ConnectivityCheckerProvider.getConnectivityChecker()
    var isDeviceConnectedToInternet by remember { mutableStateOf(false) }
    val keyValueStorage: TabulKeyStorage = TabulKeyStorageImpl()

    LaunchedEffect(key1 = Unit) {
        onAction(LocationScreenIntent.AutoFetchLocation)
        navigationEvent.collect { destination ->
            navController.navigate(route = destination)
        }
    }

    Scaffold(topBar = {
        TabulTopAppBar(
            navigateBack = navController,
            title = stringResource(Res.string.location_text),
            shouldDisplayBackButton = true,
            shouldDisplayTitle = true
        )
    }, bottomBar = {
        BottomAppBar(containerColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            tonalElevation = 0.dp,
            content = {
                TabulButton(
                    resource = Res.string.save_location_text, actionClick = {
                        coroutineScope.launch {
                            isDeviceConnectedToInternet = checker.getConnectivityState().isConnected
                            onAction(
                                LocationScreenIntent.SaveLocationActionClick(
                                    email = keyValueStorage.email.toString(),
                                    isDeviceConnectedToInternet = isDeviceConnectedToInternet
                                )
                            )
                        }
                    }, enabled = true
                )
            })
    }, content = { contentPadding ->

        when {
            locationScreenViewModelState.isLoading -> CustomLoadingDialog(
                showDialog = locationScreenViewModelState.isLoading,
                message = stringResource(Res.string.saving_location_text)
            )

            locationScreenViewModelState.responseSuccess -> {
                onAction(LocationScreenIntent.HomeActionClick)
            }

            locationScreenViewModelState.responseFailed -> {
                coroutineScope.launch {
                    if (showToast) {
                        showToast(locationScreenViewModelState.errorMessage.toString())
                        showToast = false
                    }
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize().padding(contentPadding)
            .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart,
                    content = {
                        Column(content = {
                            Text(
                                text = stringResource(Res.string.location_text),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                    fontSize = 25.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = stringResource(Res.string.third_pager_sub_heading),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                        })
                    })
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = if (locationScreenViewModelState.selectedPlace != null) {
                        locationScreenViewModelState.selectedPlace.locality.toString()
                    } else {
                        locationScreenViewModelState.address
                    },
                    onValueChange = {
                        onAction(
                            LocationScreenIntent.AddressChanged(it)
                        )
                    }, label = {
                        Text(
                            text = stringResource(Res.string.enter_location_text),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                fontSize = 13.sp
                            )
                        )
                    }, shape = RoundedCornerShape(10.dp), trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.LocationSearching,
                            contentDescription = null
                        )
                    }, singleLine = true, keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ), colors = OutlinedTextFieldDefaults.colors(
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
                    ), modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart,
                    content = {
                        locationScreenViewModelState.addressError?.let { errorMessage ->
                            Text(
                                text = errorMessage,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp,
                                    color = Color.Red
                                )
                            )
                        }
                    })
                /*Spacer(modifier = Modifier.height(20.dp))

                ExposedDropdownMenuBox(expanded = locationScreenViewModelState.isExpanded,
                    onExpandedChange = {}) {
                    TextField(
                        value = locationScreenViewModelState.searchQuery,
                        onValueChange = {
                            onAction(LocationScreenIntent.OnSearchAction(it))
                        }
                    )
                    ExposedDropdownMenu(expanded = locationScreenViewModelState.isExpanded,
                        onDismissRequest = { onAction(LocationScreenIntent.OnDropdownDismissRequest) }) {
                        if (locationScreenViewModelState.places.isNotEmpty()) {
                            locationScreenViewModelState.places.forEach { place ->
                                DropdownMenuItem(onClick = {
                                    onAction(
                                        LocationScreenIntent.OnPlaceSelected(
                                            place
                                        )
                                    )
                                }, text = {
                                    Text(
                                        text = place.locality ?: "Unknown place",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                            fontSize = 13.sp
                                        )
                                    )
                                })
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                TabulButton(
                    resource = Res.string.save_location_text,
                    actionClick = {
                        onAction(
                            LocationScreenIntent.OnSearchQueryAction
                        )
                    },
                    enabled = true
                )*/
            })
    })
}