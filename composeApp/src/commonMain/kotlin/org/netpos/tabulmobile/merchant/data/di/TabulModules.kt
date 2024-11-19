package org.netpos.tabulmobile.merchant.data.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.netpos.tabulmobile.merchant.data.remote.HttpClientFactory

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
}