package org.netpos.tabulmobile.merchant.data.remote

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import org.netpos.tabulmobile.merchant.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.merchant.domain.remote.TabulResult
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): TabulResult<T, ErrorDataTypes.Remote> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return TabulResult.Error(ErrorDataTypes.Remote.request_timeout)
    } catch(e: UnresolvedAddressException) {
        return TabulResult.Error(ErrorDataTypes.Remote.no_internet)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return TabulResult.Error(ErrorDataTypes.Remote.unknown)
    }

    return responseToTabulResult(response)
}

suspend inline fun <reified T> responseToTabulResult(
    response: HttpResponse
): TabulResult<T, ErrorDataTypes.Remote> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                TabulResult.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                TabulResult.Error(ErrorDataTypes.Remote.serialization)
            }
        }
        408 -> TabulResult.Error(ErrorDataTypes.Remote.request_timeout)
        429 -> TabulResult.Error(ErrorDataTypes.Remote.too_many_requests)
        in 500..599 -> TabulResult.Error(ErrorDataTypes.Remote.server)
        else -> TabulResult.Error(ErrorDataTypes.Remote.unknown)
    }
}