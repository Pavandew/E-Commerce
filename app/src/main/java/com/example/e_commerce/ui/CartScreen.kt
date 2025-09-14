package com.example.e_commerce.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CartScreen(vm: ProductViewModel) {
    val items by vm.cartItems.collectAsState(initial = emptyList())
    val total by vm.cartTotal.collectAsState(initial = 0.0)

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Text("Cart", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items.size) { idx ->
                val it = items[idx]
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row {
                        AsyncImage(model = it.image, contentDescription = it.title, modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(it.title)
                            Text("₹${it.price} x ${it.quantity}")
                        }
                    }
                    Button(onClick = { vm.removeFromCart(it.productId) }) { Text("Remove") }
                }
            }
        }
        Divider()
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total")
            Text("₹${"%.2f".format(total ?: 0.0)}", style = MaterialTheme.typography.titleMedium)
        }
    }
}
