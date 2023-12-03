package com.example.babysdiet.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.theme.Blue1
import com.example.babysdiet.ui.theme.SMALL_PADDING
import com.example.babysdiet.ui.theme.topAppBarBackgroumdColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableDropdownMenu(
    products: List<Product>,
    onProductSelected: (Product) -> Unit
) {

//    val categories = listOf(
//        "Food",
//        "Beverages",
//        "Sports",
//        "Learning",
//        "Travel",
//        "Rent",
//        "Bills",
//        "Fees",
//        "Others",
//    )

    var category by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    // Category Field
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = SMALL_PADDING, end = SMALL_PADDING, bottom = SMALL_PADDING)
    ) {
        Column(
            modifier = Modifier
//            .padding(30.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        expanded = false
                    }
                )
        ) {

            Text(
                modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
                text = "Category",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightTextFields)
                            .border(
                                width = 1.8.dp,
                                color = Blue1,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            },
                        label = {
                            Text(
                                text = stringResource(id = R.string.search),
//                            modifier = Modifier.padding(start = SMALL_PADDING)
                            )
                        },
                        value = category,
                        onValueChange = {
                            category = it
                            expanded = true
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
                            focusedLabelColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            cursorColor = Color.Black,
                            containerColor = Color.White
                        ),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Rounded.KeyboardArrowDown,
                                    contentDescription = "arrow",
                                    tint = Color.Black
                                )
                            }
                        }
                    )
                }

                AnimatedVisibility(visible = expanded) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(textFieldSize.width.dp),
//                    elevation = 15.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {

                        LazyColumn(
                            modifier = Modifier.heightIn(max = 500.dp),
                        ) {

                            if (category.isNotEmpty()) {
                                items(
                                    products.filter {
                                        it.name.lowercase()
                                            .contains(category.lowercase()) || it.name.lowercase()
                                            .contains("others")
                                    }
//                                    .sorted()
                                ) {
                                    CategoryItems(
                                        title = it.name,
                                        product = it
                                    ) { title ->
                                        category = title.name
                                        expanded = false
                                    }
                                }
                            } else {
                                items(
                                    products
                                ) {
                                    CategoryItems(
                                        title = it.name,
                                        product = it,
                                    ) { title ->
                                        category = title.name
                                        expanded = false
                                    }
                                }
                            }

                        }

                    }
                }

            }

        }
    }
}

@Composable
fun CategoryItems(
    title: String,
    product: Product,
    onSelect: (Product) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(product)
            }
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }

}


@Composable
@Preview
fun SearchExpandableDropdownMenuPreview() {
    SearchableDropdownMenu(
        products = arrayOf(
            Product(
                name = "milk",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
            Product(
                name = "carrot",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
            Product(
                name = "meat",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
            Product(
                name = "bread",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
            Product(
                name = "potato",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
        ).toList(),
        onProductSelected = {}
    )
}