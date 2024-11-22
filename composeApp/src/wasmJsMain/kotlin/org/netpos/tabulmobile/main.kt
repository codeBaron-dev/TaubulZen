package org.netpos.tabulmobile

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.netpos.tabulmobile.customer.data.di.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        initKoin()
        App()
    }
}