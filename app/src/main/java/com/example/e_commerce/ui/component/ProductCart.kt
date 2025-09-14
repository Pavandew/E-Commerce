package com.example.e_commerce.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce.model.Product

@Composable
fun ProductCard(
    product: Product,
    onAdd: (Product) -> Unit,
    onClick: (Product) -> Unit
) {
    Card (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable{ onClick(product)}
    ){
        Column (
            modifier = Modifier.padding(8.dp)
        ){
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier.fillMaxWidth()
                    .height(140.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(product.title, maxLines = 2, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(4.dp))

            Text("₹${product.price}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(6.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text("₹${product.rating.rate} ⭐ (${product.rating.rate})", style = MaterialTheme.typography.bodySmall)
                Button(onClick = { onAdd(product)}, contentPadding = PaddingValues(4.dp)) {
                    Text("Add")
                }
            }
        }
    }

}