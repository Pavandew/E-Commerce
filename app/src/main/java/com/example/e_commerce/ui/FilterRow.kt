package com.example.e_commerce.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterRow(
    categories: List<String>,
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    priceMax: Float,
    onPriceChange: (Float) -> Unit,
    rating: Float,
    onRatingChange: (Float) -> Unit,
    ascending: Boolean,
    onSortChange: (Boolean) -> Unit
) {

    Column (modifier = Modifier.fillMaxWidth().padding(8.dp)){
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)){
            // Category dropdown
            var expanded by remember { mutableStateOf(false) }

            Box{
                Button(
                    onClick = { expanded = true}
                ) {
                    Text(selectedCategory)
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false}) {
                    categories.forEach { cat ->
                        DropdownMenuItem(text = { Text(cat)}, onClick = { expanded = false; onCategoryChange(cat)})
                    }
                }
            }

            // Price slider (0..10000)
            Column (
                modifier = Modifier.weight(1f)
            ){
                Text("Max ₹${priceMax.toInt()} ")
                Slider(value = priceMax, onValueChange = onPriceChange, valueRange = 0f..10000f)
            }

            // Rating dropdown
            var ratingExpanded by remember { mutableStateOf(false) }
            Box{
                Button(onClick = { ratingExpanded = true }) { Text(if (rating == 0f) "Rating" else "${rating}+" )}

                DropdownMenu(expanded = ratingExpanded, onDismissRequest = { ratingExpanded = false}) {
                    listOf(0f, 1f, 2f, 3f, 4f).forEach {
                        DropdownMenuItem(text = { Text(if ( it == 0f) " All" else "${it}+")}, onClick = { ratingExpanded = false; onRatingChange})
                    }
                }
            }

            // Sort toggle
            Button(onClick = {
                onSortChange(!ascending)
            }) {
                Text(if (ascending) "Price ↑" else "Price ↓")
            }
        }
    }

}