package org.netpos.tabulmobile.shared.domain.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.account_text
import tabulmobile.composeapp.generated.resources.basket_text
import tabulmobile.composeapp.generated.resources.favourite_text
import tabulmobile.composeapp.generated.resources.home_text

sealed class TabulNavigationItems(
    val route: NavigationRoutes,
    val title: StringResource,
    val defaultIcon: ImageVector
) {

    data object Home : TabulNavigationItems(
        route = NavigationRoutes.Home,
        title = Res.string.home_text,
        defaultIcon = Icons.Outlined.Home
    )

    data object Basket : TabulNavigationItems(
        route = NavigationRoutes.Basket,
        title = Res.string.basket_text,
        defaultIcon = Icons.Outlined.ShoppingCart
    )

    data object Favourite : TabulNavigationItems(
        route = NavigationRoutes.Favourite,
        title = Res.string.favourite_text,
        defaultIcon = Icons.Outlined.FavoriteBorder
    )

    data object Account : TabulNavigationItems(
        route = NavigationRoutes.Account,
        title = Res.string.account_text,
        defaultIcon = Icons.Outlined.PersonOutline
    )
}