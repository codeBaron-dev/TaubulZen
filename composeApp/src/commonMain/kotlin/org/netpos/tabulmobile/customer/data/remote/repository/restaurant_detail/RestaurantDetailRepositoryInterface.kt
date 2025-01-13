package org.netpos.tabulmobile.customer.data.remote.repository.restaurant_detail

import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.details.RestaurantDetailResponse
import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.menu.RestaurantMenus
import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.menu_items.RestaurantMenuItemsResponse
import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.opning_hours.RestaurantOpeningHours
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface RestaurantDetailRepositoryInterface {

    suspend fun getRestaurantDetail(restaurantId: String):
            TabulResult<RestaurantDetailResponse, ErrorDataTypes.Remote>

    suspend fun getRestaurantMenus(restaurantId: String):
            TabulResult<RestaurantMenus, ErrorDataTypes.Remote>

    suspend fun getRestaurantOpeningHours(menuId: String):
            TabulResult<RestaurantOpeningHours, ErrorDataTypes.Remote>

    suspend fun getRestaurantMenuItems(menuId: String):
            TabulResult<RestaurantMenuItemsResponse, ErrorDataTypes.Remote>
}