package org.netpos.tabulmobile.merchant.data.remote.repository.login

import org.netpos.tabulmobile.merchant.data.login.remote.login.payload.LoginPayloadModel
import org.netpos.tabulmobile.merchant.data.login.remote.login.response.LoginResponseModel
import org.netpos.tabulmobile.merchant.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.merchant.domain.remote.TabulResult

interface LoginRepositoryInterface {

    suspend fun login(loginPayloadModel: LoginPayloadModel): TabulResult<LoginResponseModel, ErrorDataTypes.Remote>
}