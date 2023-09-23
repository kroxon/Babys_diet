package com.example.babysdiet.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.theme.EVALUATOIN_INDICATOR_SIZE
import com.example.babysdiet.ui.theme.LARGE_PADDING
import com.example.babysdiet.ui.theme.SMALL_PADDING

@Composable
fun ProductAdvanceItem(product: Product) {
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
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = MaterialTheme.typography.titleLarge.fontSize
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
fun ProductAdvanceItemPreview() {
    ProductAdvanceItem(
        product = Product(
            name = "allergen",
            categoryId = 1,
            description = "",
            isAllergen = true
        )
    )
}