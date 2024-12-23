@file:OptIn(ExperimentalMaterial3Api::class)

package org.netpos.tabulmobile.shared.presentation.utils

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.LocationSearching
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.netpos.tabulmobile.shared.presentation.theme.tabulColor
import tabulmobile.composeapp.generated.resources.MontserratAlternates_Regular
import tabulmobile.composeapp.generated.resources.MontserratAlternates_SemiBold
import tabulmobile.composeapp.generated.resources.Res
import tabulmobile.composeapp.generated.resources.dummy_location_text
import tabulmobile.composeapp.generated.resources.enter_new_address_text
import tabulmobile.composeapp.generated.resources.recent_address_text
import tabulmobile.composeapp.generated.resources.update_location_text

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

@Composable
fun SharedOtpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester? = null,
    isInputValid: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.length <= 1) {
                onValueChange(it)
                if (it.isNotEmpty()) {
                    nextFocusRequester?.requestFocus()
                }
            }
        },
        isError = value.isEmpty() && !isInputValid,
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .focusRequester(focusRequester),
        singleLine = true,
        textStyle = MaterialTheme.typography.labelMedium.copy(
            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
            fontSize = 13.sp
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.Black,
            errorTextColor = Color.Red,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            cursorColor = tabulColor,
            errorCursorColor = Color.Red,
            selectionColors = LocalTextSelectionColors.current,
            focusedBorderColor = tabulColor,
            unfocusedBorderColor = Color.Gray,
            errorBorderColor = Color.Red,
        )
    )
}

@Composable
fun SharedOnboardImage(
    painter: DrawableResource
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = Modifier,
        content = {
            Image(
                modifier = Modifier.width(404.dp).height(380.dp),
                /*colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),*/
                painter = painterResource(painter),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }
    )
}

@Composable
fun SharedLocationBottomSheet(sheetState: ModalBottomSheetState) {
    val coroutineScope = rememberCoroutineScope()

    Surface(
        content = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp), // Removed unnecessary status bar padding
                content = {
                    // Top Row with close button and title
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        sheetState.hide()
                                    }
                                },
                                content = {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = null
                                    )
                                }
                            )
                            Text(
                                text = stringResource(Res.string.update_location_text),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                                    fontSize = 16.sp
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // OutlinedTextField for email
                    OutlinedTextField(
                        value = "",
                        onValueChange = { /* Handle text input */ },
                        label = {
                            Text(
                                text = stringResource(Res.string.enter_new_address_text),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                    fontSize = 13.sp
                                )
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.LocationSearching,
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.LightGray,
                            errorTextColor = Color.Red,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.onSurface,
                            errorCursorColor = Color.Red,
                            selectionColors = LocalTextSelectionColors.current,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(Res.string.recent_address_text),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontFamily = FontFamily(Font(Res.font.MontserratAlternates_SemiBold)),
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        content = {
                            items(20) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                                    content = {
                                        Icon(
                                            imageVector = Icons.Outlined.MyLocation,
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = stringResource(Res.string.dummy_location_text),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                                                fontSize = 13.sp
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun EmptyUiState(
    message: String,
    painter: DrawableResource
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Image(
                modifier = Modifier.width(150.dp).height(150.dp),
                painter = painterResource(painter),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily(Font(Res.font.MontserratAlternates_Regular)),
                    fontSize = 13.sp
                )
            )
        }
    )
}