package com.example.e_commerce.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CartScreen(vm: ProductViewModel) {
    val items by vm.cartItems.collectAsState(initial = emptyList())
    val total by vm.cartTotal.collectAsState(initial = 0.0)

    Column(modifier = Modifier.fillMaxSize().padding(8.dp, top = 35.dp)) {
        Text("Cart", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items.size) { idx ->
                val it = items[idx]
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row {
                        AsyncImage(model = it.image, contentDescription = it.title, modifier = Modifier.size(50.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f) // take available space
                            ) {
                                Text(
                                    text = it.title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis // truncate with "..."
                                )
                                Text("₹${it.price} x ${it.quantity}")
                            }

                            Button(onClick = { vm.removeFromCart(it.productId) }) { Text("Remove", maxLines = 1) }
                        }
                    }
                }
            }
        }
        Divider()
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp, bottom = 30.dp, top = 5.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", style = MaterialTheme.typography.titleMedium, fontSize = 25.sp)
            Text("₹${"%.2f".format(total ?: 0.0)}", style = MaterialTheme.typography.titleMedium, fontSize = 25.sp, modifier = Modifier.padding(end = 15.dp))
        }
    }
}
