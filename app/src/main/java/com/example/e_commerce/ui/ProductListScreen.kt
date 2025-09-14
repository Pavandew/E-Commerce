package com.example.e_commerce.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.e_commerce.model.Product
import com.example.e_commerce.ui.component.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    vm: ProductViewModel = viewModel(),
    onOpenDetails: (Product) -> Unit,
    onOpenCart: () -> Unit
) {
    val pagingItems = vm.productsPagingFlow.collectAsLazyPagingItems()
    val categories by vm.categories.collectAsState()
    val filtered by vm.filteredProducts.collectAsState()
    val filter by vm.filter.collectAsState()   // collect filter state

    // Filter UI state
    var selectedCategory by remember { mutableStateOf("All") }
    var priceMax by remember { mutableStateOf(0f) } // slider 0..10000 mapped
    var selectedRating by remember { mutableStateOf(0f) }
    var ascending by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                actions = {
                    IconButton(onClick = onOpenCart) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        },
        bottomBar = {
            Spacer(modifier = Modifier.height(56.dp))
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Filters row
            FilterRow(
                categories = listOf("All") + categories,
                selectedCategory = selectedCategory,
                onCategoryChange = {
                    selectedCategory = it
                    applyFilterDebounced(vm, selectedCategory, priceMax, selectedRating, ascending)
                },
                priceMax = priceMax,
                onPriceChange = { new ->
                    priceMax = new
                    applyFilterDebounced(vm, selectedCategory, priceMax, selectedRating, ascending)
                },
                rating = selectedRating,
                onRatingChange = {
                    selectedRating = it
                    applyFilterDebounced(vm, selectedCategory, priceMax, selectedRating, ascending)
                },
                ascending = ascending,
                onSortChange = {
                    ascending = it
                    applyFilterDebounced(vm, selectedCategory, priceMax, selectedRating, ascending)
                }
            )

            //  Fixed check: using collected filter
            if (filter != ProductFilter()) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(filtered) { product ->
                        if (product != null) {
                            ProductCard(
                                product,
                                onAdd = { vm.addToCart(it) },
                                onClick = onOpenDetails
                            )
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(pagingItems.itemCount) { index ->
                        val product = pagingItems[index]
                        if (product != null) {
                            ProductCard(
                                product,
                                onAdd = { vm.addToCart(it) },
                                onClick = onOpenDetails
                            )
                        }
                    }
                }
            }
        }
    }
}
