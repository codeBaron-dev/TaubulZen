package org.netpos.tabulmobile.merchant.presentation.onboard.ui.pagers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.merchant.presentation.onboard.ui.SharedPageBottomBarView
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.third_pager_heading
import tabulmobile.composeapp.generated.resources.third_pager_sub_heading

@Composable
fun ThirdOnboardSliderRoot() {
    Scaffold(
        bottomBar = {
            SharedPageBottomBarView(
                header = stringResource(Res.string.third_pager_heading),
                subHeader = stringResource(Res.string.third_pager_sub_heading),
                isThisFirstPager = false,
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
                            TextButton(onClick = { }, content = {
                                Text(
                                    text = "",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                        fontSize = 13.sp
                                    )
                                )
                            })
                        }
                    )
                    Spacer(modifier = Modifier.height(145.dp))
                    BoxWithConstraints(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier,
                        content = {
                            /*Image(
                                modifier = Modifier
                                    .width(304.dp).height(280.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                                imageVector = SplashScreenInteraction,
                                contentScale = ContentScale.FillBounds,
                                contentDescription = null
                            )*/
                        }
                    )
                }
            )
        }
    )
}