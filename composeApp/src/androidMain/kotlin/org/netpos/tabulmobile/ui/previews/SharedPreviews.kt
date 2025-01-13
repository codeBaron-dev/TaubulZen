package org.netpos.tabulmobile.ui.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.netpos.tabulmobile.customer.presentation.restaurant_details.ui.FoodItemCard
import org.netpos.tabulmobile.shared.presentation.utils.CustomLoadingDialog

@Preview
@Composable
fun CustomLoadingDialogPreview() {
    CustomLoadingDialog(showDialog = true, message = "Loading...")
}

@Preview(showBackground = true)
@Composable
fun FoodItemCardPreview() {
    MaterialTheme {
        FoodItemCard(
            foodName = "Chicken Wings",
            foodDescription = "6 pieces of chicken wings",
            foodPrice = "12,000",
            imageUrl = "https://images.unsplash.com/photo-1589923188900-852eedbd3e8d",
            onAddClicked = { /* TODO */ }
        )
    }
}