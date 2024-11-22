package org.netpos.tabulmobile.customer.data.remote.repository.register

import org.netpos.tabulmobile.customer.data.models.register.remote.payload.RegistrationPayloadModel
import org.netpos.tabulmobile.customer.data.models.register.remote.response.RegistrationResponseModel
import org.netpos.tabulmobile.customer.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult
import org.netpos.tabulmobile.customer.domain.remote.map

class RegistrationRepository(
    private val remoteDataSource: RemoteDataSource
) : RegistrationRepositoryInterface {

    override suspend fun register(registrationPayloadModel: RegistrationPayloadModel): TabulResult<RegistrationResponseModel, ErrorDataTypes.Remote> {
        return remoteDataSource.register(registrationPayloadModel = registrationPayloadModel)
            .map { it }
    }
}