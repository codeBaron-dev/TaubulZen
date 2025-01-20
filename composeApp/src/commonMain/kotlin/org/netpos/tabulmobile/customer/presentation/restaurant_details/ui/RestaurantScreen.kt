package org.netpos.tabulmobile.customer.presentation.restaurant_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorage
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorageImpl
import org.netpos.tabulmobile.customer.presentation.restaurant_details.view_model.RestaurantDetailScreenIntent
import org.netpos.tabulmobile.customer.presentation.restaurant_details.view_model.RestaurantDetailScreenState
import org.netpos.tabulmobile.customer.presentation.restaurant_details.view_model.RestaurantDetailScreenViewModel
import org.netpos.tabulmobile.shared.data.formatTimeToReadable
import org.netpos.tabulmobile.shared.data.formatToNaira
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import org.netpos.tabulmobile.shared.presentation.utils.CustomLoadingDialog
import org.netpos.tabulmobile.shared.presentation.utils.EmptyUiState
import org.netpos.tabulmobile.shared.presentation.utils.TabulButton
import org.netpos.tabulmobile.shared.presentation.utils.TabulRestaurantTopBar
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Bold
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.dummy_promotion_text
import tabulmobile.composeapp.generated.resources.empty_screen_state
import tabulmobile.composeapp.generated.resources.loading_text
import tabulmobile.composeapp.generated.resources.opening_hours_text
import tabulmobile.composeapp.generated.resources.view_product_item_text
import tabulmobile.composeapp.generated.resources.your_restaurant_list_is_empty_text

@Composable
fun RestaurantScreenRoot(
    navController: NavHostController,
    restaurantDetailScreenViewModel: RestaurantDetailScreenViewModel
) {

    val restaurantDetailScreenUiState by restaurantDetailScreenViewModel.state.collectAsState(
        initial = RestaurantDetailScreenState()
    )

    RestaurantScreen(
        restaurantDetailScreenViewModel = restaurantDetailScreenViewModel,
        onAction = { intent ->
            restaurantDetailScreenViewModel.sendIntent(intent = intent)
        },
        navigationEvents = restaurantDetailScreenViewModel.navigationIntent,
        restaurantDetailScreenState = restaurantDetailScreenUiState,
        navController = navController
    )
}

@Composable
fun RestaurantScreen(
    restaurantDetailScreenViewModel: RestaurantDetailScreenViewModel,
    onAction: (RestaurantDetailScreenIntent) -> Unit,
    navigationEvents: SharedFlow<NavigationRoutes>,
    restaurantDetailScreenState: RestaurantDetailScreenState,
    navController: NavHostController
) {

    val coroutineScope = rememberCoroutineScope()
    val keyValueStorage: TabulKeyStorage = TabulKeyStorageImpl()
    var selectedTabIndex by remember { mutableStateOf(0) }
    var menuId by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Select a day") }
    var triggerMenu by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        if (keyValueStorage.restaurantInformation != null) {
            onAction(RestaurantDetailScreenIntent.FetchMenus(restaurantId = keyValueStorage.restaurantInformation?.restaurantId.toString()))
        }
        navigationEvents.collect { destination ->
            navController.navigate(route = destination)
        }
    }

    Scaffold(
        topBar = {
            TabulRestaurantTopBar(
                navigateBack = navController,
                title = "Restaurant"
            )
        },
        bottomBar = {},
        content = { contentPadding ->
            println(keyValueStorage.restaurantInformation)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(horizontal = 16.dp),
                content = {

                    when {
                        restaurantDetailScreenState.isMenuLoading -> CustomLoadingDialog(
                            showDialog = true,
                            message = stringResource(Res.string.loading_text)
                        )
                    }

                    LazyColumn(
                        content = {
                            item {
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp)),
                                    content = {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            content = {
                                                Box {
                                                    AsyncImage(
                                                        model = keyValueStorage.restaurantInformation?.restaurantDetailImage,
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(120.dp),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .align(Alignment.TopStart)
                                                            .background(Color.Red.copy(alpha = 0.8f))
                                                            .padding(8.dp),
                                                        content = {
                                                            if (keyValueStorage.restaurantInformation?.isOnPromo == 0) {
                                                                Text(
                                                                    text = stringResource(Res.string.dummy_promotion_text),
                                                                    color = Color.White,
                                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                Res.font.MontserratAlternates_Regular
                                                                            )
                                                                        ),
                                                                        fontSize = 13.sp
                                                                    ),
                                                                    modifier = Modifier.align(
                                                                        Alignment.Center
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    )
                                                    /*IconButton(
                                                        onClick = {  },
                                                        modifier = Modifier
                                                            .align(Alignment.TopEnd)
                                                            .padding(8.dp)
                                                            .background(
                                                                color = Color.White.copy(alpha = 0.8f),
                                                                shape = CircleShape
                                                            )
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.FavoriteBorder,
                                                            contentDescription = null,
                                                            tint = Color.Red
                                                        )
                                                    }*/
                                                }

                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(6.dp),
                                                    content = {
                                                        Text(
                                                            text = keyValueStorage.restaurantInformation?.name
                                                                ?: stringResource(Res.string.loading_text),
                                                            style = MaterialTheme.typography.titleLarge.copy(
                                                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Bold)),
                                                                fontSize = 20.sp
                                                            )
                                                        )
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            modifier = Modifier.padding(vertical = 3.dp),
                                                            content = {
                                                                Icon(
                                                                    imageVector = Icons.Default.Star,
                                                                    contentDescription = null,
                                                                    tint = Color(0xFFFFD700)
                                                                )
                                                                Spacer(modifier = Modifier.width(4.dp))
                                                                Text(
                                                                    text = keyValueStorage.restaurantInformation?.rating
                                                                        ?: stringResource(Res.string.loading_text),
                                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                Res.font.MontserratAlternates_SemiBold
                                                                            )
                                                                        ),
                                                                        fontSize = 13.sp
                                                                    )
                                                                )
                                                            }
                                                        )
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            content = {
                                                                Icon(
                                                                    imageVector = Icons.Outlined.LocationOn,
                                                                    contentDescription = null
                                                                )
                                                                Spacer(modifier = Modifier.width(4.dp))
                                                                Text(
                                                                    text = keyValueStorage.restaurantInformation?.address
                                                                        ?: stringResource(Res.string.loading_text),
                                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                Res.font.MontserratAlternates_Regular
                                                                            )
                                                                        ),
                                                                        fontSize = 13.sp
                                                                    )
                                                                )
                                                            }
                                                        )
                                                        when {
                                                            restaurantDetailScreenState.isOpeningHoursResponseSuccess -> {
                                                                if (restaurantDetailScreenState.isOpeningHoursResponseSuccess) {
                                                                    Row(
                                                                        modifier = Modifier.fillMaxWidth(),
                                                                        horizontalArrangement = Arrangement.spacedBy(
                                                                            20.dp
                                                                        ),
                                                                        verticalAlignment = Alignment.CenterVertically,
                                                                        content = {
                                                                            Row(
                                                                                horizontalArrangement = Arrangement.Start,
                                                                                verticalAlignment = Alignment.CenterVertically,
                                                                                content = {
                                                                                    Icon(
                                                                                        imageVector = Icons.Outlined.Timer,
                                                                                        contentDescription = null
                                                                                    )
                                                                                    Spacer(
                                                                                        modifier = Modifier.width(
                                                                                            4.dp
                                                                                        )
                                                                                    )
                                                                                    TextButton(
                                                                                        onClick = {
                                                                                            expanded =
                                                                                                !expanded
                                                                                        },
                                                                                        content = {
                                                                                            Text(
                                                                                                text = stringResource(
                                                                                                    Res.string.opening_hours_text
                                                                                                ),
                                                                                                style = MaterialTheme.typography.bodySmall.copy(
                                                                                                    fontFamily = FontFamily(
                                                                                                        Font(
                                                                                                            Res.font.MontserratAlternates_Regular
                                                                                                        )
                                                                                                    ),
                                                                                                    fontSize = 13.sp
                                                                                                ),
                                                                                            )

                                                                                            DropdownMenu(
                                                                                                expanded = expanded,
                                                                                                onDismissRequest = {
                                                                                                    expanded =
                                                                                                        false
                                                                                                },
                                                                                                modifier = Modifier.fillMaxWidth()
                                                                                            ) {
                                                                                                restaurantDetailScreenState.openingHours?.data?.forEach { openingHour ->
                                                                                                    val formattedOpenTime =
                                                                                                        formatTimeToReadable(
                                                                                                            openingHour?.open.toString()
                                                                                                        )
                                                                                                    val formattedCloseTime =
                                                                                                        formatTimeToReadable(
                                                                                                            openingHour?.close.toString()
                                                                                                        )
                                                                                                    DropdownMenuItem(
                                                                                                        onClick = {
                                                                                                            selectedOptionText =
                                                                                                                "${openingHour?.day}: $formattedOpenTime - $formattedCloseTime"
                                                                                                            expanded =
                                                                                                                false
                                                                                                        },
                                                                                                        text = {
                                                                                                            Text(
                                                                                                                "${openingHour?.day}: $formattedOpenTime - $formattedCloseTime"
                                                                                                            )
                                                                                                        }
                                                                                                    )
                                                                                                }
                                                                                            }
                                                                                        })
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
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            item {
                                when {
                                    restaurantDetailScreenState.isMenuResponseSuccess -> {
                                        if (restaurantDetailScreenState.isMenuResponseSuccess) {
                                            val menus =
                                                restaurantDetailScreenState.restaurantMenus?.data
                                            if (triggerMenu) {
                                                coroutineScope.launch {
                                                    onAction(
                                                        RestaurantDetailScreenIntent.FetchMenuCategoryClicked(
                                                            menus?.get(0)?.categoryId.toString()
                                                        )
                                                    )
                                                }
                                            }
                                            ScrollableTabRow(
                                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                                modifier = Modifier.fillMaxWidth(),
                                                selectedTabIndex = selectedTabIndex,
                                                divider = {},
                                                tabs = {
                                                    menus?.forEachIndexed { index, menu ->
                                                        Tab(
                                                            text = {
                                                                Text(
                                                                    text = menu?.categoryName.toString(),
                                                                    style = MaterialTheme.typography.labelMedium.copy(
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                Res.font.MontserratAlternates_SemiBold
                                                                            )
                                                                        ),
                                                                        fontSize = 13.sp
                                                                    )
                                                                )
                                                            },
                                                            selected = selectedTabIndex == index,
                                                            onClick = {
                                                                coroutineScope.launch {
                                                                    selectedTabIndex = index
                                                                    onAction(
                                                                        RestaurantDetailScreenIntent.FetchMenuCategoryClicked(
                                                                            menu?.categoryId.toString()
                                                                        )
                                                                    )
                                                                }
                                                            },
                                                            selectedContentColor = tabulColor,
                                                            unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                                                            modifier = Modifier.fillMaxWidth()
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    )
                    when {
                        restaurantDetailScreenState.isMenuCategoriesLoading -> CustomLoadingDialog(
                            showDialog = true,
                            message = stringResource(Res.string.loading_text)
                        )

                        restaurantDetailScreenState.isMenuCategoriesResponseSuccess -> {
                            if (restaurantDetailScreenState.isMenuCategoriesResponseSuccess) {
                                triggerMenu = false
                                LazyColumn(
                                    content = {
                                        restaurantDetailScreenState.menuCategories?.data?.let { foodCategories ->
                                            items(foodCategories) { food ->
                                                FoodItemCard(
                                                    foodName = food?.name
                                                        ?: stringResource(Res.string.loading_text),
                                                    foodDescription = food?.description
                                                        ?: stringResource(Res.string.loading_text),
                                                    foodPrice = formatToNaira(amount = food?.price.toString()),
                                                    imageUrl = food?.menuImage.toString(),
                                                    onAddClicked = { /* TODO */ }
                                                )
                                            }
                                        }
                                    }
                                )
                            } else {
                                EmptyUiState(
                                    message = stringResource(Res.string.your_restaurant_list_is_empty_text),
                                    painter = Res.drawable.empty_screen_state
                                )
                            }
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun FoodItemCard(
    foodName: String,
    foodDescription: String,
    foodPrice: String,
    imageUrl: String,
    onAddClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 5.dp),
        shape = RoundedCornerShape(12.dp),
        content = {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Text(
                                text = foodName,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                    fontSize = 13.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = foodDescription,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = foodPrice,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Bold)),
                                    fontSize = 13.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            TabulButton(
                                resource = Res.string.view_product_item_text,
                                actionClick = {},
                                enabled = true
                            )
                        }
                    )
                }
            )
        }
    )
}