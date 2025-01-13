package org.netpos.tabulmobile.customer.data.remote.repository.restaurant_detail

import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.details.RestaurantDetailResponse
import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.menu.RestaurantMenus
import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.menu_items.RestaurantMenuItemsResponse
import org.netpos.tabulmobile.customer.data.models.restaurant_details.response.opning_hours.RestaurantOpeningHours
import org.netpos.tabulmobile.customer.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult
import org.netpos.tabulmobile.customer.domain.remote.map

class RestaurantDetailRepository(
    private val remoteDataSource: RemoteDataSource
) : RestaurantDetailRepositoryInterface {
    override suspend fun getRestaurantDetail(restaurantId: String):
            TabulResult<RestaurantDetailResponse, ErrorDataTypes.Remote> {
        return remoteDataSource.getRestaurantDetail(restaurantId = restaurantId).map { it }
    }

    override suspend fun getRestaurantMenus(restaurantId: String):
            TabulResult<RestaurantMenus, ErrorDataTypes.Remote> {
        return remoteDataSource.getRestaurantMenus(restaurantId = restaurantId).map { it }
    }

    override suspend fun getRestaurantOpeningHours(menuId: String):
            TabulResult<RestaurantOpeningHours, ErrorDataTypes.Remote> {
        return remoteDataSource.getRestaurantOpeningHours(menuId = menuId).map { it }
    }

    override suspend fun getRestaurantMenuItems(menuId: String):
            TabulResult<RestaurantMenuItemsResponse, ErrorDataTypes.Remote> {
        return remoteDataSource.getRestaurantMenuItems(menuId = menuId).map { it }
    }
}