@file:OptIn(ExperimentalMaterial3Api::class)

package org.netpos.tabulmobile.merchant.presentation.onboard.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.merchant.presentation.onboard.ui.pagers.FirstOnboardSliderRoot
import org.netpos.tabulmobile.merchant.presentation.onboard.ui.pagers.ForthOnboardSliderRoot
import org.netpos.tabulmobile.merchant.presentation.onboard.ui.pagers.SecondOnboardSliderRoot
import org.netpos.tabulmobile.merchant.presentation.onboard.ui.pagers.ThirdOnboardSliderRoot
import org.netpos.tabulmobile.merchant.presentation.onboard.viewmodel.OnboardScreenIntent
import org.netpos.tabulmobile.merchant.presentation.onboard.viewmodel.OnboardScreenViewModel
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import org.stakeny.stakeny.shared.domain.navigation.NavigationRoutes
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Bold
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.get_started_text
import tabulmobile.composeapp.generated.resources.login_text
import tabulmobile.composeapp.generated.resources.next_text
import tabulmobile.composeapp.generated.resources.register_text

@Composable
fun OnboardScreenRoot(
    navController: NavHostController,
    onboardScreenViewModel: OnboardScreenViewModel
) {

    LaunchedEffect(key1 = Unit) {
        onboardScreenViewModel.navigationEvent.collect { destination ->
            navController.navigate(route = destination) {
                popUpTo(route = NavigationRoutes.Onboarding) { inclusive = true }
            }
        }
    }

    OnboardScreen(onboardScreenViewModel = onboardScreenViewModel)
}

@Composable
fun OnboardScreen(onboardScreenViewModel: OnboardScreenViewModel) {

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(0, 0f) { 4 }

    val isUserAtGetStartedPager = when (pagerState.currentPage) {
        0 -> false
        1 -> false
        2 -> true
        else -> false
    }
    val isThisFirstPager = when (pagerState.currentPage) {
        0 -> true
        1 -> false
        2 -> false
        else -> false
    }
    val isLastPager = when (pagerState.currentPage) {
        0 -> false
        1 -> false
        2 -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                tonalElevation = 0.dp,
                content = {
                    PagerBottomBar(
                        onboardScreenViewModel,
                        pagerState,
                        isUserAtGetStartedPager = isUserAtGetStartedPager,
                        isLastPager = isLastPager
                    )
                }
            )
        },
        content = {
            HorizontalPager(state = pagerState) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (pagerState.currentPage) {
                            0 -> {
                                FirstOnboardSliderRoot(
                                    skipToLastPager = {
                                        coroutineScope.launch(Dispatchers.Unconfined) {
                                            pagerState.scrollToPage(page = 3)
                                        }
                                    }
                                )
                            }

                            1 -> {
                                SecondOnboardSliderRoot(
                                    skipToLastPager = {
                                        coroutineScope.launch(Dispatchers.Unconfined) {
                                            pagerState.scrollToPage(page = 3)
                                        }
                                    }
                                )
                            }

                            2 -> {
                                ThirdOnboardSliderRoot()
                            }

                            3 -> {
                                ForthOnboardSliderRoot()
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PagerBottomBar(
    onboardScreenViewModel: OnboardScreenViewModel,
    pagerState: PagerState,
    isUserAtGetStartedPager: Boolean,
    isLastPager: Boolean
) {

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            if (isLastPager) {
                OutlinedButton(
                    onClick = { onboardScreenViewModel.sendIntent(OnboardScreenIntent.LoginActionClick) },
                    modifier = Modifier
                        .width(150.dp)
                        .height(42.dp),
                    border = BorderStroke(
                        1.dp,
                        color = tabulColor
                    ),
                    content = {
                        Text(
                            text = stringResource(Res.string.login_text),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                fontSize = 13.sp,
                                color = tabulColor
                            )
                        )
                    }
                )
                Button(
                    onClick = { onboardScreenViewModel.sendIntent(OnboardScreenIntent.RegisterActionClick) },
                    modifier = Modifier
                        .width(150.dp)
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = tabulColor),
                    content = {
                        Text(
                            text = stringResource(Res.string.register_text),
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                fontSize = 13.sp,
                                color = Color.White
                            )
                        )
                    }
                )
            } else {
                Row(
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Circle,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = when (pagerState.currentPage) {
                                0 -> tabulColor
                                else -> if (isSystemInDarkTheme()) Color.White else Color.DarkGray
                            }
                        )
                        Icon(
                            imageVector = Icons.Filled.Circle,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = when (pagerState.currentPage) {
                                1 -> tabulColor
                                else -> if (isSystemInDarkTheme()) Color.White else Color.DarkGray
                            }
                        )
                        Icon(
                            imageVector = Icons.Filled.Circle,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = when (pagerState.currentPage) {
                                2 -> tabulColor
                                else -> if (isSystemInDarkTheme()) Color.White else Color.DarkGray
                            }
                        )
                    }
                )

                if (isUserAtGetStartedPager) {
                    Button(
                        onClick = {
                            when (pagerState.currentPage) {
                                2 -> coroutineScope.launch(Dispatchers.Unconfined) {
                                    pagerState.scrollToPage(
                                        page = 3
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .height(42.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = tabulColor),
                        content = {
                            Text(
                                text = stringResource(Res.string.get_started_text),
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            )
                        }
                    )
                } else {
                    Button(
                        onClick = {
                            when (pagerState.currentPage) {
                                0 -> coroutineScope.launch(Dispatchers.Unconfined) {
                                    pagerState.scrollToPage(
                                        page = 1
                                    )
                                }

                                1 -> coroutineScope.launch(Dispatchers.Unconfined) {
                                    pagerState.scrollToPage(
                                        page = 2
                                    )
                                }

                                2 -> coroutineScope.launch(Dispatchers.Unconfined) {
                                    pagerState.scrollToPage(
                                        page = 3
                                    )
                                }

                                3 -> coroutineScope.launch(Dispatchers.Unconfined) {
                                    pagerState.scrollToPage(
                                        page = 3
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (pagerState.currentPage) {
                                0 -> Color.White
                                else -> tabulColor
                            }
                        ),
                        content = {
                            Text(
                                text = stringResource(Res.string.next_text),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                    fontSize = 13.sp,
                                    color = when (pagerState.currentPage) {
                                        0 -> tabulColor
                                        else -> Color.White
                                    }
                                )
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun SharedPageBottomBarView(
    header: String,
    subHeader: String,
    isThisFirstPager: Boolean,
) {
    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth().height(344.dp)
            .padding(horizontal = 16.dp),
        tonalElevation = 0.dp,
        content = {
            BoxWithConstraints(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier,
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        content = {
                            Text(
                                text = header,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Bold)),
                                    fontWeight = FontWeight(weight = 600),
                                    fontSize = 31.sp,
                                    color = if (isThisFirstPager) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = subHeader,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontWeight = FontWeight(weight = 500),
                                    fontSize = 13.sp,
                                    color = if (isThisFirstPager) {
                                        Color.White
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    }
                                )
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                        }
                    )
                }
            )
        }
    )
}