package org.netpos.tabulmobile.customer.data.remote

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import org.netpos.tabulmobile.customer.data.remote.network.TabulConstants.SERVER_ERROR_MESSAGE
import org.netpos.tabulmobile.customer.data.remote.network.model.RemoteErrorModel
import org.netpos.tabulmobile.customer.domain.remote.ErrorDataTypes
import org.netpos.tabulmobile.customer.domain.remote.TabulResult
import org.netpos.tabulmobile.shared.data.formatErrorMessage
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): TabulResult<T, ErrorDataTypes.Remote> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return TabulResult.Error(SERVER_ERROR_MESSAGE)
    } catch(e: UnresolvedAddressException) {
        return TabulResult.Error(SERVER_ERROR_MESSAGE)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return TabulResult.Error(SERVER_ERROR_MESSAGE)
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
                TabulResult.Error("")
            }
        }
        else -> {
            try {
                val error = response.body<RemoteErrorModel>()
                TabulResult.Error(error = formatErrorMessage(error.errors))
            } catch(exception: Exception) {
                TabulResult.Error(error = exception.message ?: SERVER_ERROR_MESSAGE)
            }
        }
    }
}