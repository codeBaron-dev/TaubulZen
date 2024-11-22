package org.netpos.tabulmobile.customer.data.remote.network

import org.netpos.tabulmobile.customer.data.models.login.remote.login.payload.LoginPayloadModel
import org.netpos.tabulmobile.customer.data.models.login.remote.login.response.LoginResponseModel
import org.netpos.tabulmobile.customer.data.models.register.remote.payload.RegistrationPayloadModel
import org.netpos.tabulmobile.customer.data.models.register.remote.response.RegistrationResponseModel
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface RemoteDataSource {

    suspend fun login(loginPayloadModel: LoginPayloadModel): TabulResult<LoginResponseModel, ErrorDataTypes.Remote>

    suspend fun register(registrationPayloadModel: RegistrationPayloadModel): TabulResult<RegistrationResponseModel, ErrorDataTypes.Remote>
}