package org.netpos.tabulmobile.customer.data.remote.repository.home

import org.netpos.tabulmobile.customer.data.models.home_screen.response.HomeSpecialRestaurantResponse
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface HomeRepositoryInterface {

    suspend fun homeScreenRestaurantsInfo(): TabulResult<HomeSpecialRestaurantResponse, ErrorDataTypes.Remote>
}