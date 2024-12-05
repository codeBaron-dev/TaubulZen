package org.netpos.tabulmobile.customer.presentation.onboard.ui.pagers

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.customer.presentation.onboard.ui.SharedPageBottomBarView
import org.netpos.tabulmobile.shared.presentation.utils.SharedOnboardImage
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.forth_pager_heading
import tabulmobile.composeapp.generated.resources.forth_pager_sub_heading
import tabulmobile.composeapp.generated.resources.onboard_people_icon

@Composable
fun ForthOnboardSliderRoot() {
    Scaffold(
        bottomBar = {
            SharedPageBottomBarView(
                header = stringResource(Res.string.forth_pager_heading),
                subHeader = stringResource(Res.string.forth_pager_sub_heading),
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
                            TextButton(onClick = {  }, content = {
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
                    SharedOnboardImage(painter = Res.drawable.onboard_people_icon)
                }
            )
        }
    )
}