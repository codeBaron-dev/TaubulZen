package org.netpos.tabulmobile.customer.data.remote.repository.location

import org.netpos.tabulmobile.customer.data.models.location.payload.LocationPayloadModel
import org.netpos.tabulmobile.customer.data.models.location.response.LocationResponseModel
import org.netpos.tabulmobile.customer.data.models.location.response.saved.SavedUserLocationResponse
import org.netpos.tabulmobile.customer.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult
import org.netpos.tabulmobile.customer.domain.remote.map

class LocationRepository(
    private val remoteDataSource: RemoteDataSource
) : LocationRepositoryInterface {
    override suspend fun uploadUserLocation(
        locationPayloadModel: LocationPayloadModel
    ): TabulResult<LocationResponseModel, ErrorDataTypes.Remote> {
        return remoteDataSource.uploadUserLocation(
            locationPayloadModel = locationPayloadModel
        ).map { it }
    }

    override suspend fun retrievedSavedLocation(email: String):
            TabulResult<SavedUserLocationResponse, ErrorDataTypes.Remote> {
        return remoteDataSource.retrievedSavedLocation(email = email).map { it }
    }
}