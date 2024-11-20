package org.netpos.tabulmobile.shared.presentation.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.Font
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.Res

@Composable
fun CustomLoadingDialog(
    showDialog: Boolean,
    message: String
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { /* Prevent dialog dismissal */ }) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            CircularProgressIndicator(
                                color = tabulColor,
                                trackColor = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = message,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            )
                        }
                    )
                }
            )
        }
    }
}