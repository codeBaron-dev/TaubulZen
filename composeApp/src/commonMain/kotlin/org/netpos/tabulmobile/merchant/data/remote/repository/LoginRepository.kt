package org.netpos.tabulmobile.merchant.data.remote.repository

import org.netpos.tabulmobile.merchant.data.login.remote.login.payload.LoginPayloadModel
import org.netpos.tabulmobile.merchant.data.login.remote.login.response.LoginResponseModel
import org.netpos.tabulmobile.merchant.data.remote.network.RemoteDataSource
import org.netpos.tabulmobile.merchant.data.remote.repository.login.LoginRepositoryInterface
import org.netpos.tabulmobile.merchant.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.merchant.domain.remote.TabulResult
import org.netpos.tabulmobile.merchant.domain.remote.map

class LoginRepository(
    private val remoteDataSource: RemoteDataSource
) : LoginRepositoryInterface {

    override suspend fun login(
        loginPayloadModel: LoginPayloadModel
    ): TabulResult<LoginResponseModel, ErrorDataTypes.Remote> {
        return remoteDataSource.login(loginPayloadModel = loginPayloadModel).map { it }
    }
}