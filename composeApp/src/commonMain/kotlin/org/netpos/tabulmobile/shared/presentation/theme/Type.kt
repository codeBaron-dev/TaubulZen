package org.netpos.tabulmobile.shared.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Black
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Bold
import tabulmobile.composeapp.generated.resources.MontserratAlternates_ExtraBold
import tabulmobile.composeapp.generated.resources.MontserratAlternates_ExtraLight
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Light
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Medium
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Thin
import tabulmobile.composeapp.generated.resources.Res


@Composable
fun MontserratAlternatesFontType() = FontFamily(
    Font(Res.font.MontserratAlternates_Light, weight = FontWeight.Light),
    Font(Res.font.MontserratAlternates_ExtraLight, weight = FontWeight.ExtraLight),
    Font(Res.font.MontserratAlternates_Medium, weight = FontWeight.Medium),
    Font(Res.font.MontserratAlternates_Bold, weight = FontWeight.Bold),
    Font(Res.font.MontserratAlternates_SemiBold, weight = FontWeight.SemiBold),
    Font(Res.font.MontserratAlternates_ExtraBold, weight = FontWeight.ExtraBold),
    Font(Res.font.MontserratAlternates_Regular, weight = FontWeight.Normal),
    Font(Res.font.MontserratAlternates_Black, weight = FontWeight.Black),
    Font(Res.font.MontserratAlternates_Thin, weight = FontWeight.Thin),
)

@Composable
fun AppTypography() = Typography().run {
    val fontFamily = MontserratAlternatesFontType()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily),
    )
}

