package org.netpos.tabulmobile

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToastContext(this)
        installSplashScreen()
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = if (isSystemInDarkTheme()) Color.WHITE else Color.BLACK,
                    darkScrim = if (isSystemInDarkTheme()) Color.BLACK else Color.WHITE
                ),
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = if (isSystemInDarkTheme()) Color.WHITE else Color.BLACK,
                    darkScrim = if (isSystemInDarkTheme()) Color.BLACK else Color.WHITE
                )
            )
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}