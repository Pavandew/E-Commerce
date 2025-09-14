package com.example.e_commerce.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.e_commerce.model.Product

@Composable
fun AppNavHost(vm: ProductViewModel = viewModel()) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "list") {
        composable("list") {
            ProductListScreen(vm, onOpenDetails = { product ->
                nav.currentBackStackEntry?.savedStateHandle?.set("product", product)
                nav.navigate("details")
            }, onOpenCart = { nav.navigate("cart") })
        }
        composable("details") {
            val product = nav.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
            if (product != null) ProductDetailScreen(product, onAdd = { vm.addToCart(it) })
        }
        composable("cart") { CartScreen(vm) }
    }
}
