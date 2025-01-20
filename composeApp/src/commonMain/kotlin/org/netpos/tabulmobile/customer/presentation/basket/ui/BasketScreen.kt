package org.netpos.tabulmobile.customer.presentation.basket.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.customer.data.remote.network.TabulConstants.DUMMY_IMAGE
import org.netpos.tabulmobile.customer.presentation.basket.view_model.BasketScreenIntent
import org.netpos.tabulmobile.customer.presentation.basket.view_model.BasketScreenState
import org.netpos.tabulmobile.customer.presentation.basket.view_model.BasketScreenViewModel
import org.netpos.tabulmobile.shared.domain.navigation.NavigationRoutes
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import org.netpos.tabulmobile.shared.presentation.utils.TabulSmallButton
import org.netpos.tabulmobile.shared.presentation.utils.TabulTopAppBar
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Bold
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.add_more_items_text
import tabulmobile.composeapp.generated.resources.basket_text
import tabulmobile.composeapp.generated.resources.checkout_text
import tabulmobile.composeapp.generated.resources.dummy_delivery_fee_text
import tabulmobile.composeapp.generated.resources.dummy_location_text
import tabulmobile.composeapp.generated.resources.dummy_restaurant_name_text
import tabulmobile.composeapp.generated.resources.my_basket_tab_text
import tabulmobile.composeapp.generated.resources.orders_tab_text

@Composable
fun BasketScreenRoot(
    navController: NavHostController,
    basketScreenViewModel: BasketScreenViewModel
) {

    val basketScreenUiState by basketScreenViewModel.state.collectAsState(initial = BasketScreenState())

    BasketScreen(
        onAction = { intent ->
            basketScreenViewModel.sendIntent(intent = intent)
        },
        basketScreenState = basketScreenUiState,
        navigationEvents = basketScreenViewModel.navigationIntent,
        navController = navController
    )
}

@Composable
fun BasketScreen(
    onAction: (BasketScreenIntent) -> Unit,
    basketScreenState: BasketScreenState,
    navigationEvents: SharedFlow<NavigationRoutes>,
    navController: NavHostController,
) {

    var tabIndex by remember { mutableStateOf(0) }
    val tabsTitle = listOf(
        stringResource(Res.string.my_basket_tab_text),
        stringResource(Res.string.orders_tab_text)
    )

    LaunchedEffect(key1 = Unit) {
        navigationEvents.collect { destination -> navController.navigate(route = destination) }
    }

    Scaffold(
        topBar = {
            TabulTopAppBar(
                navigateBack = navController,
                title = stringResource(Res.string.basket_text),
                shouldDisplayBackButton = true,
                shouldDisplayTitle = true
            )
        },
        bottomBar = {},
        content = { contentPadding ->

            Column(
                modifier = Modifier.padding(contentPadding).padding(horizontal = 16.dp),
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
                            LazyColumn(
                                content = {
                                    items(10) { MyBasketScreen() }
                                }
                            )
                        }

                        1 -> {
                            OrdersScreen()
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun MyBasketScreen(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 5.dp),
        shape = RoundedCornerShape(12.dp),
        content = {
            Column(
                modifier = modifier.fillMaxWidth().padding(16.dp),
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            // Food Image
                            AsyncImage(
                                model = DUMMY_IMAGE,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Food Details
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        content = {
                                            IconButton(
                                                onClick = {},
                                                content = {
                                                    Icon(
                                                        imageVector = Icons.Default.DeleteForever,
                                                        contentDescription = null,
                                                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                                                    )
                                                }
                                            )
                                        }
                                    )
                                    Text(
                                        text = stringResource(Res.string.dummy_restaurant_name_text),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                            fontSize = 13.sp
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = stringResource(Res.string.dummy_location_text),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                            fontSize = 13.sp
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = stringResource(Res.string.dummy_delivery_fee_text),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Bold)),
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            TabulSmallButton(
                                resource = Res.string.add_more_items_text,
                                actionClick = {},
                                enabled = true,
                                color = MaterialTheme.colorScheme.onSurface,
                                textColor = if (isSystemInDarkTheme()) Color.Black else Color.White
                            )
                            TabulSmallButton(
                                resource = Res.string.checkout_text,
                                actionClick = {},
                                enabled = true,
                                color = tabulColor,
                                textColor = Color.White
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun OrdersScreen(modifier: Modifier = Modifier) {
}