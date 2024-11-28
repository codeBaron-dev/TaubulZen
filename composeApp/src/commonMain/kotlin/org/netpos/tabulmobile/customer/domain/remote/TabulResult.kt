package org.netpos.tabulmobile.customer.domain.remote

sealed interface TabulResult<out D, out E: ErrorDataTypes> {
    data class Success<out D>(val data: D): TabulResult<D, Nothing>
    data class Error<out E>(val error: String): TabulResult<E, Nothing>
}

inline fun <T, E: ErrorDataTypes, R> TabulResult<T, E>.map(map: (T) -> R): TabulResult<R, E> {
    return when(this) {
        is TabulResult.Error -> TabulResult.Error(error)
        is TabulResult.Success -> TabulResult.Success(map(data))
    }
}

fun <T, E: ErrorDataTypes> TabulResult<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: ErrorDataTypes> TabulResult<T, E>.onSuccess(action: (T) -> Unit): TabulResult<T, E> {
    return when(this) {
        is TabulResult.Error -> this
        is TabulResult.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: ErrorDataTypes> TabulResult<T, E>.onError(action: (String) -> Unit): TabulResult<T, E> {
    return when(this) {
        is TabulResult.Error -> {
            action(error)
            this
        }
        is TabulResult.Success -> this
    }
}

typealias EmptyResult<E> = TabulResult<Unit, E>