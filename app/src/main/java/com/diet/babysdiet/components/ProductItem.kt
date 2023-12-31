package com.diet.babysdiet.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.diet.babysdiet.R
import com.diet.babysdiet.components.data.models.Product
import com.diet.babysdiet.ui.theme.EVALUATOIN_INDICATOR_SIZE
import com.diet.babysdiet.ui.theme.SMALL_PADDING

@Composable
fun ProductItem(product: Product) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(start = SMALL_PADDING)
            .fillMaxWidth()
    ) {
        Text(
//            modifier = Modifier
//                .padding(LARGE_PADDING),
            text = product.name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (product.isAllergen) {
            Image(
                painter = painterResource(id = R.drawable.ic_alert),
                contentDescription = "allergen alert",
                modifier = Modifier
                    .padding(
                        start = SMALL_PADDING
                    )
                    .size(EVALUATOIN_INDICATOR_SIZE)
            )
        }
    }
}

@Composable
@Preview
fun ProductItemPreview() {
    ProductItem(
        product = Product(
            name = "allergen",
            categoryId = 1,
            description = "",
            isAllergen = true
        )
    )
}