package org.netpos.tabulmobile.customer.data.remote.repository.register

import org.netpos.tabulmobile.customer.data.models.register.remote.payload.RegistrationPayloadModel
import org.netpos.tabulmobile.customer.data.models.register.remote.response.RegistrationResponseModel
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface RegistrationRepositoryInterface {

    suspend fun register(registrationPayloadModel: RegistrationPayloadModel): TabulResult<RegistrationResponseModel, ErrorDataTypes.Remote>
}