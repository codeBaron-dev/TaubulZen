package org.netpos.tabulmobile.customer.data.remote.repository.home

import org.netpos.tabulmobile.customer.data.models.home_screen.response.HomeSpecialRestaurantResponse
import org.netpos.tabulmobile.customer.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult
import org.netpos.tabulmobile.customer.domain.remote.map

class HomeRepository(
    private val remoteDataSource: RemoteDataSource
) : HomeRepositoryInterface {
    override suspend fun homeScreenRestaurantsInfo():
            TabulResult<HomeSpecialRestaurantResponse, ErrorDataTypes.Remote> {
        return remoteDataSource.homeScreenRestaurantsInfo().map { it }
    }
}