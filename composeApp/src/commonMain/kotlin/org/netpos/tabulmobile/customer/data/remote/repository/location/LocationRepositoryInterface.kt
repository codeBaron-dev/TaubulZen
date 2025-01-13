package org.netpos.tabulmobile.customer.data.remote.repository.location

import org.netpos.tabulmobile.customer.data.models.location.payload.LocationPayloadModel
import org.netpos.tabulmobile.customer.data.models.location.response.LocationResponseModel
import org.netpos.tabulmobile.customer.data.models.location.response.saved.SavedUserLocationResponse
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface LocationRepositoryInterface {

    suspend fun uploadUserLocation(
        locationPayloadModel: LocationPayloadModel
    ): TabulResult<LocationResponseModel, ErrorDataTypes.Remote>

    suspend fun retrievedSavedLocation(email: String):
            TabulResult<SavedUserLocationResponse, ErrorDataTypes.Remote>
}