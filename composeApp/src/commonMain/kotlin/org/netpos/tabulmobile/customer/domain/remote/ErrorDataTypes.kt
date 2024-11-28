package org.netpos.tabulmobile.customer.domain.remote

interface ErrorDataTypes: ErrorInterface {
    enum class Remote: ErrorDataTypes {
        request_timeout,
        too_many_requests,
        no_internet,
        server,
        serialization,
        unknown,
        unauthorized
    }

    enum class Local: ErrorDataTypes {
        disk_full,
        unknown
    }
}