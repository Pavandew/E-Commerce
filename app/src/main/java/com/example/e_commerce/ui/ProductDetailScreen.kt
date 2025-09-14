package com.example.e_commerce.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce.model.Product

@Composable
fun ProductDetailScreen(product: Product, onAdd: (Product) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        AsyncImage(model = product.image, contentDescription = product.title, modifier = Modifier.fillMaxWidth().height(260.dp))

        Spacer(modifier = Modifier.height(8.dp))

        Text(product.title, style = MaterialTheme.typography.headlineSmall)
        Text("₹${product.price}", style = MaterialTheme.typography.titleMedium)
        Text("Rating: ${product.rating.rate} (${product.rating.count})", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(8.dp))

        Text(product.description, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { onAdd(product) }, modifier = Modifier.fillMaxWidth()) { Text("Add to Cart") }
    }
}
