package org.netpos.tabulmobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform