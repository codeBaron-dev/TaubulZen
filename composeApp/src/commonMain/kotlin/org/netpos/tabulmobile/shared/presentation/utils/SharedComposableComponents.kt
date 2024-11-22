@file:OptIn(ExperimentalMaterial3Api::class)

package org.netpos.tabulmobile.shared.presentation.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
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

@Composable
fun TabulTopAppBar(
    navigateBack: NavHostController,
    title: String,
    shouldDisplayBackButton: Boolean,
    shouldDisplayTitle: Boolean
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            if (shouldDisplayTitle) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter,
                    content = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                fontSize = 13.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        },
        navigationIcon = {
            if (shouldDisplayBackButton) {
                IconButton(onClick = { navigateBack.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun TabulButton(
    resource: StringResource,
    actionClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        modifier = Modifier.fillMaxWidth().height(42.dp),
        onClick = {
            actionClick.invoke()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = tabulColor),
        content = {
            Text(
                text = stringResource(resource = resource),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                    fontSize = 13.sp,
                    color = Color.White
                )
            )
        }
    )
}