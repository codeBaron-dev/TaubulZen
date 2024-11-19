package org.netpos.tabulmobile.merchant.presentation.onboard.ui.pagers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.merchant.presentation.onboard.ui.SharedPageBottomBarView
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.first_pager_heading
import tabulmobile.composeapp.generated.resources.first_pager_sub_heading
import tabulmobile.composeapp.generated.resources.skip_text

@Composable
fun FirstOnboardSliderRoot(skipToLastPager: () -> Unit) {
    FirstOnboardSlider( skipToLastPager = skipToLastPager)
}

@Composable
fun FirstOnboardSlider(skipToLastPager: () -> Unit) {
    Scaffold(
        containerColor = tabulColor,
        bottomBar = {
            SharedPageBottomBarView(
                header = stringResource(Res.string.first_pager_heading),
                subHeader = stringResource(Res.string.first_pager_sub_heading),
                isThisFirstPager = true,
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).padding(padding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd,
                        content = {
                            TextButton(onClick = { skipToLastPager() }, content = {
                                Text(
                                    text = stringResource(Res.string.skip_text),
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                        fontSize = 13.sp,
                                        color = Color.White
                                    )
                                )
                            })
                        }
                    )
                    Spacer(modifier = Modifier.height(145.dp))

                }
            )
        }
    )
}