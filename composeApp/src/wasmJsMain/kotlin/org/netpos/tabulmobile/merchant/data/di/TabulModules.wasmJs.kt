package org.netpos.tabulmobile.merchant.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Js.create() } // HTTP client for JS
    // single { JsSettings() }
    }