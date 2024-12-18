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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
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
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import org.netpos.tabulmobile.shared.presentation.utils.SharedLocationBottomSheet
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Bold
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.dummy_delivery_fee_text
import tabulmobile.composeapp.generated.resources.dummy_delivery_time_text
import tabulmobile.composeapp.generated.resources.dummy_location_text
import tabulmobile.composeapp.generated.resources.dummy_promotion_text
import tabulmobile.composeapp.generated.resources.dummy_rating_text
import tabulmobile.composeapp.generated.resources.dummy_restaurant_name_text
import tabulmobile.composeapp.generated.resources.food_image
import tabulmobile.composeapp.generated.resources.home_location_text
import tabulmobile.composeapp.generated.resources.popular_brand_text
import tabulmobile.composeapp.generated.resources.promotions_text
import tabulmobile.composeapp.generated.resources.searching_text
import tabulmobile.composeapp.generated.resources.top_meals_text

@Composable
fun HomeScreenRoot() {

    HomeScreen()
}

@Composable
fun HomeScreen() {

    val categories = listOf(
        Pair("Food Deals", "🛍️"),
        Pair("Local Dishes", "🥗"),
        Pair("Fast Food", "🍔"),
        Pair("Pizza & more", "🍕")
    )

    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val suggestions = listOf("Restaurant", "Food", "Cafe", "Bar")
    var isDeliverySelected by remember { mutableStateOf(true) }
    var isRestaurantSelected by remember { mutableStateOf(false) }
    var isSearchBarClicked by remember { mutableStateOf(false) }
    val insets = WindowInsets.statusBars.asPaddingValues()

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val isSheetVisible = sheetState.currentValue != ModalBottomSheetValue.Hidden

    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

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
                                    Text(
                                        text = stringResource(Res.string.dummy_location_text),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                            fontSize = 13.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = tabulColor
                                    )
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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                            .padding(horizontal = 16.dp),
                        content = {
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
                            item {
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    content = {
                                        items(categories) { category ->
                                            Surface(
                                                color = MaterialTheme.colorScheme.surfaceContainer,
                                                shape = RoundedCornerShape(16.dp),
                                                modifier = Modifier
                                                    .size(width = 120.dp, height = 100.dp),
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
                                                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
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
                            item {
                                Text(
                                    text = stringResource(Res.string.promotions_text),
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
                                        items(10) {
                                            FoodCard()
                                        }
                                    }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(Res.string.popular_brand_text),
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
                                        items(10) {
                                            FoodCard()
                                        }
                                    }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(Res.string.top_meals_text),
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
                                        items(10) {
                                            FoodCard()
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
                                },
                                onSearch = {
                                    isSearchBarClicked = false
                                    active = false
                                    // Perform search logic here
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
                            LazyColumn {
                                items(suggestions.filter {
                                    it.contains(
                                        searchQuery,
                                        ignoreCase = true
                                    )
                                }) { suggestion ->
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
                                            isSearchBarClicked = false
                                            active = false
                                        }
                                    )
                                }
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
fun FoodCard() {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box {
                Image(
                    painter = painterResource(Res.drawable.food_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
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
                IconButton(
                    onClick = { /* Handle favorite click */ },
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
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Text(
                    text = stringResource(Res.string.dummy_restaurant_name_text),
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
                            text = stringResource(Res.string.dummy_rating_text),
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
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(Res.string.dummy_location_text),
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
                                    imageVector = Icons.Default.Timer,
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
    }
}