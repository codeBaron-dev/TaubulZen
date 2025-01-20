@file:OptIn(ExperimentalMaterial3Api::class)

package org.netpos.tabulmobile.customer.presentation.home.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.ConnectivityCheckerProvider
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorage
import org.netpos.tabulmobile.customer.data.local.shared_preferences.TabulKeyStorageImpl
import org.netpos.tabulmobile.customer.data.models.home_screen.response.Data
import org.netpos.tabulmobile.customer.data.models.home_screen.response.HomeSpecialRestaurantResponse
import org.netpos.tabulmobile.customer.data.models.home_screen.response.RestaurantInformation
import org.netpos.tabulmobile.customer.presentation.home.view_model.HomeScreenIntent
import org.netpos.tabulmobile.customer.presentation.home.view_model.HomeScreenState
import org.netpos.tabulmobile.customer.presentation.home.view_model.HomeScreenViewModel
import org.netpos.tabulmobile.customer.presentation.restaurant_details.ui.FoodItemCard
import org.netpos.tabulmobile.shared.data.formatToNaira
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import org.netpos.tabulmobile.shared.presentation.utils.CustomLoadingDialog
import org.netpos.tabulmobile.shared.presentation.utils.EmptyUiState
import org.netpos.tabulmobile.showToast
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Bold
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.dummy_delivery_fee_text
import tabulmobile.composeapp.generated.resources.dummy_delivery_time_text
import tabulmobile.composeapp.generated.resources.dummy_location_text
import tabulmobile.composeapp.generated.resources.dummy_promotion_text
import tabulmobile.composeapp.generated.resources.empty_screen_state
import tabulmobile.composeapp.generated.resources.food_image
import tabulmobile.composeapp.generated.resources.home_location_text
import tabulmobile.composeapp.generated.resources.loading_text
import tabulmobile.composeapp.generated.resources.meals_tab__text
import tabulmobile.composeapp.generated.resources.restaurant_tab_text
import tabulmobile.composeapp.generated.resources.search_image
import tabulmobile.composeapp.generated.resources.search_meals_or_restaurant_text
import tabulmobile.composeapp.generated.resources.searching_text
import tabulmobile.composeapp.generated.resources.your_restaurant_list_is_empty_text

@Composable
fun HomeScreenRoot(
    sheetState: ModalBottomSheetState,
    homeScreenViewModel: HomeScreenViewModel,
    navController: NavHostController
) {
    val homeScreenState by homeScreenViewModel.state.collectAsState(initial = HomeScreenState())

    HomeScreen(
        homeScreenViewModel = homeScreenViewModel,
        sheetState = sheetState,
        onAction = { intent ->
            homeScreenViewModel.sendIntent(intent)
        },
        navigationEvents = homeScreenViewModel.navigationEvent,
        homeScreenState = homeScreenState,
        navController = navController
    )
}

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    sheetState: ModalBottomSheetState,
    onAction: (HomeScreenIntent) -> Unit,
    navigationEvents: SharedFlow<NavigationRoutes>,
    homeScreenState: HomeScreenState,
    navController: NavHostController,
) {

    val categories = listOf(
        Pair("Food Deals", "🛍️"),
        Pair("Local Dishes", "🥗"),
        Pair("Fast Food", "🍔"),
        Pair("Pizza & more", "🍕")
    )

    val restaurantState = rememberLazyListState()
    var showToast by remember { mutableStateOf(false) }
    val checker = ConnectivityCheckerProvider.getConnectivityChecker()
    var isDeviceConnectedToInternet by remember { mutableStateOf(false) }
    val keyValueStorage: TabulKeyStorage = TabulKeyStorageImpl()
    var specialRestaurantResponse by remember { mutableStateOf<HomeSpecialRestaurantResponse?>(null) }
    val insets = WindowInsets.statusBars.asPaddingValues()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        showToast = true
        launch { isDeviceConnectedToInternet = checker.getConnectivityState().isConnected }
        launch {
            homeScreenViewModel.getHomeScreenData(
                isDeviceConnectedToInternet = isDeviceConnectedToInternet,
                keyValueStorage = keyValueStorage
            )
        }
        navigationEvents.collect { destination ->
            navController.navigate(route = destination)
        }
    }

    Scaffold(
        modifier = Modifier.padding(insets),
        topBar = {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                content = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = tabulColor
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(Res.string.home_location_text),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Row(
                                modifier = Modifier.weight(1f),
                                content = {
                                    TextButton(onClick = {
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    }, content = {
                                        Text(
                                            text = stringResource(Res.string.dummy_location_text),
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                                fontSize = 13.sp
                                            )
                                        )
                                    })
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(onClick = {
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    }, content = {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = tabulColor
                                        )
                                    })
                                }
                            )

                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                            )
                        }
                    )
                    HomeTopBar()
                }
            )
        },
        bottomBar = {},
        content = { contentPadding ->
            when {
                homeScreenState.isLoading -> {
                    CustomLoadingDialog(
                        showDialog = homeScreenState.isLoading,
                        message = stringResource(Res.string.loading_text)
                    )
                }

                homeScreenState.responseSuccess -> {
                    specialRestaurantResponse = homeScreenState.homeSpecialRestaurantResponse
                }

                homeScreenState.responseFailed -> {
                    coroutineScope.launch {
                        if (showToast) {
                            showToast(homeScreenState.errorMessage.toString())
                            showToast = false
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(horizontal = 16.dp),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = {
                            if (specialRestaurantResponse?.data.isNullOrEmpty()) {
                                EmptyUiState(
                                    message = stringResource(Res.string.your_restaurant_list_is_empty_text),
                                    painter = Res.drawable.empty_screen_state
                                )
                            } else {
                                LazyColumn(
                                    modifier = Modifier,
                                    content = {
                                        //Ad banner
                                        item {
                                            Image(
                                                painter = painterResource(Res.drawable.food_image),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(100.dp)
                                                    .clip(shape = RoundedCornerShape(5.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                        //Restaurant categories
                                        item {
                                            LazyRow(
                                                state = restaurantState,
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                                content = {
                                                    items(categories) { category ->

                                                        Surface(
                                                            color = MaterialTheme.colorScheme.surfaceContainer,
                                                            shape = RoundedCornerShape(16.dp),
                                                            modifier = Modifier
                                                                .size(
                                                                    width = 120.dp,
                                                                    height = 100.dp
                                                                ),
                                                            content = {
                                                                Column(
                                                                    modifier = Modifier
                                                                        .fillMaxSize()
                                                                        .padding(8.dp),
                                                                    verticalArrangement = Arrangement.SpaceAround,
                                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                                ) {
                                                                    Text(
                                                                        text = category.second,
                                                                        fontSize = 40.sp
                                                                    )
                                                                    Text(
                                                                        text = category.first,
                                                                        style = MaterialTheme.typography.labelMedium.copy(
                                                                            fontFamily = FontFamily(
                                                                                Font(Res.font.MontserratAlternates_Regular)
                                                                            ),
                                                                            fontSize = 16.sp,
                                                                            textAlign = TextAlign.Center
                                                                        )
                                                                    )
                                                                }
                                                            }
                                                        )
                                                    }
                                                }
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                        //Special restaurant
                                        specialRestaurantResponse?.data?.let { response ->
                                            items(response) { restaurant ->
                                                RestaurantCard(
                                                    keyValueStorage = keyValueStorage,
                                                    restaurant = restaurant,
                                                    onAction = onAction
                                                )
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
    )
}

@Composable
fun HomeTopBar() {

    val keyValueStorage: TabulKeyStorage = TabulKeyStorageImpl()
    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val suggestions = listOf("Restaurant", "Food", "Cafe", "Bar")
    var isDeliverySelected by remember { mutableStateOf(true) }
    var isRestaurantSelected by remember { mutableStateOf(false) }
    var isSearchBarClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        content = {
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    SearchBar(
                        inputField = {
                            SearchBarDefaults.InputField(
                                query = searchQuery,
                                onQueryChange = {
                                    searchQuery = it
                                    isSearchBarClicked = true
                                    keyValueStorage.searchHistory = listOf(it)
                                },
                                onSearch = {
                                    isSearchBarClicked = false
                                    active = false
                                    //TODO: Perform search logic here
                                },
                                expanded = active,
                                onExpandedChange = {
                                    active = it
                                },
                                enabled = true,
                                placeholder = { Text(stringResource(Res.string.searching_text)) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    if (searchQuery.isNotEmpty()) {
                                        IconButton(onClick = {
                                            searchQuery = ""
                                            isSearchBarClicked = false
                                        }, content = {
                                            Icon(
                                                Icons.Default.Close,
                                                contentDescription = null
                                            )
                                        })
                                    }
                                },
                                interactionSource = null,
                            )
                        },
                        expanded = active,
                        onExpandedChange = { active = it },
                        shape = SearchBarDefaults.inputFieldShape,
                        colors = SearchBarDefaults.colors(
                            dividerColor = tabulColor
                        ),
                        tonalElevation = 0.dp,
                        windowInsets = if (active) {
                            SearchBarDefaults.windowInsets
                        } else {
                            WindowInsets(0.dp)
                        },
                        content = {
                            keyValueStorage.searchHistory = suggestions
                            if (keyValueStorage.searchHistory.isNullOrEmpty()) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    content = {
                                        EmptyUiState(
                                            message = stringResource(Res.string.search_meals_or_restaurant_text),
                                            painter = Res.drawable.search_image
                                        )
                                    }
                                )
                            } else {
                                Column(
                                    content = {
                                        val searchHistory = keyValueStorage.searchHistory
                                        searchHistory?.let { history ->
                                            val filteredHistory = history.filter {
                                                it.contains(
                                                    searchQuery,
                                                    ignoreCase = true
                                                )
                                            }
                                            filteredHistory.forEachIndexed({ index, suggestion ->
                                                ListItem(
                                                    headlineContent = {
                                                        Row(
                                                            content = {
                                                                Icon(
                                                                    imageVector = Icons.Default.History,
                                                                    contentDescription = null
                                                                )
                                                                Spacer(modifier = Modifier.width(8.dp))
                                                                Text(suggestion)
                                                            }
                                                        )
                                                    },
                                                    modifier = Modifier.clickable {
                                                        searchQuery = suggestion
                                                        //isSearchBarClicked = false
                                                        //active = false
                                                    }
                                                )
                                            })
                                        }
                                        RemoteSearchedResultItem()
                                    }
                                )
                            }
                        },
                        modifier = if (active) {
                            Modifier
                                .animateContentSize(spring(stiffness = Spring.StiffnessHigh))
                                .weight(1f)
                        } else {
                            Modifier
                                .padding(start = 12.dp, top = 2.dp, end = 12.dp, bottom = 12.dp)
                                .fillMaxWidth()
                                .animateContentSize(spring(stiffness = Spring.StiffnessHigh))
                                .weight(1f)
                        }
                    )
                    if (!active) {
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = {}, content = {
                            Icon(
                                imageVector = Icons.Outlined.FilterList,
                                contentDescription = null
                            )
                        })
                    }
                    /*if (!isSearchBarClicked) {
                        Row(
                            modifier = Modifier.width(100.dp)
                                .clip(RoundedCornerShape(32.dp)),
                            verticalAlignment = Alignment.CenterVertically,
                            content = {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            isDeliverySelected = true
                                            isRestaurantSelected = false
                                        }
                                        .background(if (isDeliverySelected) tabulColor else MaterialTheme.colorScheme.onSurface)
                                        .padding(vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.DeliveryDining,
                                        contentDescription = "Delivery Icon",
                                        tint = if (isDeliverySelected) Color.White else MaterialTheme.colorScheme.surface
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            isRestaurantSelected = true
                                            isDeliverySelected = false
                                        }
                                        .background(if (isRestaurantSelected) tabulColor else MaterialTheme.colorScheme.onSurface)
                                        .padding(vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Restaurant,
                                        contentDescription = "Dine-in Icon",
                                        tint = if (isRestaurantSelected) Color.White else MaterialTheme.colorScheme.surface
                                    )
                                }
                            }
                        )
                    }*/
                }
            )
        }
    )
}

@Composable
fun RestaurantCard(
    keyValueStorage: TabulKeyStorage,
    restaurant: Data,
    onAction: (HomeScreenIntent) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = restaurant.name ?: stringResource(Res.string.loading_text),
        style = MaterialTheme.typography.labelLarge.copy(
            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
            fontSize = 16.sp
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            restaurant.data?.let { restaurant ->
                items(restaurant) { data ->
                    RestaurantCardContent(
                        data = data,
                        onAction = onAction,
                        keyValueStorage = keyValueStorage
                    )
                }
            }
        }
    )
}

@Composable
fun RestaurantCardContent(
    data: RestaurantInformation,
    onAction: (HomeScreenIntent) -> Unit,
    keyValueStorage: TabulKeyStorage
) {
    val maxLength = 26 // Maximum character length
    val coroutineScope = rememberCoroutineScope()

    val truncatedText = if (data.name.toString().length > maxLength) {
        data.name?.take(maxLength) + "..."
    } else {
        data.name
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                coroutineScope.launch {
                    keyValueStorage.restaurantInformation = data
                    onAction(HomeScreenIntent.RestaurantClicked)
                }
            },
        content = {
            Column(
                modifier = Modifier.width(300.dp),
                content = {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        content = {
                            /*Image(
                                painter = painterResource(Res.drawable.food_image),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )*/
                            AsyncImage(
                                model = data.restaurantDetailImage,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth().fillMaxHeight(),
                                contentScale = ContentScale.Crop
                            )
                            if (data.isOnPromo == 0) {
                                Box(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .align(Alignment.TopStart)
                                        .background(Color.Red.copy(alpha = 0.8f))
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(Res.string.dummy_promotion_text),
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                            fontSize = 13.sp
                                        ),
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                            IconButton(
                                onClick = { /* Handle favorite click */ },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .background(
                                        color = Color.White.copy(alpha = 0.8f),
                                        shape = CircleShape
                                    ),
                                content = {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                            )
                        }
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    ) {
                        Text(
                            text = truncatedText ?: stringResource(Res.string.loading_text),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Bold)),
                                fontSize = 16.sp
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp),
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD700)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = data.rating ?: stringResource(Res.string.loading_text),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
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
                                    text = data.address ?: stringResource(Res.string.loading_text),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                        fontSize = 13.sp
                                    )
                                )
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
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
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = stringResource(Res.string.dummy_delivery_time_text),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                                fontSize = 13.sp
                                            )
                                        )
                                    }
                                )
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Icon(
                                            imageVector = Icons.Default.DeliveryDining,
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = stringResource(Res.string.dummy_delivery_fee_text),
                                            style = MaterialTheme.typography.bodySmall.copy(
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
            )
        }
    )
}

@Composable
fun RemoteSearchedResultItem() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabsTitle = listOf(
        stringResource(Res.string.restaurant_tab_text),
        stringResource(Res.string.meals_tab__text)
    )

    Column(
        content = {
            TabRow(
                selectedTabIndex = tabIndex,
                divider = {},
                tabs = {
                    tabsTitle.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                        fontSize = 13.sp
                                    )
                                )
                            },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index },
                            selectedContentColor = tabulColor,
                            unselectedContentColor = Color.Gray
                        )
                    }
                }
            )
            when (tabIndex) {
                0 -> {

                }

                1 -> {
                    LazyColumn(
                        modifier = Modifier.padding(top = 5.dp),
                        content = {
                            items(10) {
                                FoodItemCard(
                                    foodName = "Pizza",
                                    foodDescription = "Delicious pizza with cheese and sauce",
                                    foodPrice = formatToNaira(amount = "3500"),
                                    imageUrl = "",
                                    onAddClicked = { /* Handle item click */ }
                                )
                            }
                        }
                    )
                }
            }
        }
    )
}