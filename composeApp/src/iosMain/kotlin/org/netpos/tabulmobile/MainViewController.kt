package org.netpos.tabulmobile

import androidx.compose.ui.window.ComposeUIViewController
import org.netpos.tabulmobile.customer.data.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }