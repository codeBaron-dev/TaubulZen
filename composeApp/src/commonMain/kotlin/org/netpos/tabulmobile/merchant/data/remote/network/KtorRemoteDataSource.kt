package org.netpos.tabulmobile.merchant.data.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import org.netpos.tabulmobile.merchant.data.login.remote.login.payload.LoginPayloadModel
import org.netpos.tabulmobile.merchant.data.login.remote.login.response.LoginResponseModel
import org.netpos.tabulmobile.merchant.data.remote.safeCall
import org.netpos.tabulmobile.merchant.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.merchant.domain.remote.TabulResult

class KtorRemoteDataSource(
    private val httpClient: HttpClient
) : RemoteDataSource {

    override suspend fun login(
        loginPayloadModel: LoginPayloadModel
    ): TabulResult<LoginResponseModel, ErrorDataTypes.Remote> {
        return safeCall<LoginResponseModel> {
            httpClient.post {
                url(urlString = ApiConfig.LOGIN_ENDPOINT)
                setBody(loginPayloadModel)
            }
        }
    }
}