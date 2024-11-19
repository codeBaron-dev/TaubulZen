package org.netpos.tabulmobile.merchant.domain.remote

interface ErrorDataTypes: ErrorInterface {
    enum class Remote: ErrorDataTypes {
        request_timeout,
        too_many_requests,
        no_internet,
        server,
        serialization,
        unknown
    }

    enum class Local: ErrorDataTypes {
        disk_full,
        unknown
    }
}