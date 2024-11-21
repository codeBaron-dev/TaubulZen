package org.netpos.tabulmobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun showToast(message: String)