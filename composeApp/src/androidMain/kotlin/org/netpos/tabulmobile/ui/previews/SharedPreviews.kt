package org.netpos.tabulmobile.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.netpos.tabulmobile.shared.presentation.utils.CustomLoadingDialog

@Preview
@Composable
fun CustomLoadingDialogPreview() {
    CustomLoadingDialog(showDialog = true, message = "Loading...")
}