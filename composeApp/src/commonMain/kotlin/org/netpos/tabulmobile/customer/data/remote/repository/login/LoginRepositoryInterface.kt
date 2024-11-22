package org.netpos.tabulmobile.customer.data.remote.repository.login

import org.netpos.tabulmobile.customer.data.models.login.remote.login.payload.LoginPayloadModel
import org.netpos.tabulmobile.customer.data.models.login.remote.login.response.LoginResponseModel
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult

interface LoginRepositoryInterface {

    suspend fun login(loginPayloadModel: LoginPayloadModel): TabulResult<LoginResponseModel, ErrorDataTypes.Remote>
}