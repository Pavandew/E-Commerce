package com.example.e_commerce

import com.example.e_commerce.Api.ProductApi
import com.example.e_commerce.data.cart.CartDao
import com.example.e_commerce.data.cart.CartItem
import com.example.e_commerce.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// In this repo combines network + cartDB; includes client-side filter flow used with debounce
class ProductRepository(
    private val api: ProductApi,
    private val cartDao: CartDao
) {
    suspend fun fetchAll(): List<Product> = api.getAll()
    suspend fun getCategories(): List<String> = api.getCategories()
    suspend fun getByCategories(category: String): List<Product> = api.getByCategory(category)

    // Cart
    fun cartFlow(): Flow<List<CartItem>> = cartDao.getAll()
    suspend fun addToCart(item: CartItem) = cartDao.insert(item)
    suspend fun removeToCart(productId: Int) = cartDao.remove(productId)
    fun cartTotal(): Flow<Double?> = cartDao.totalPrice()

    // Client-side filtering helper
    fun filterProducts(
        all: List<Product>,
        category: String?,
        minPrice: Double?,
        maxPrice: Double?,
        minRating: Double?,
        ascending: Boolean?
    ): Flow<List<Product>> = flow {
        var list = all
        category?.takeIf { it.isNotBlank() }?.let { cat ->
            list = list.filter { it.category.equals(cat, ignoreCase = true) }
        }

        minPrice?.let { list = list.filter { p -> p.price >= it } }
        maxPrice?.let { list = list.filter { p -> p.price <= it } }
        minRating?.let { list = list.filter { p -> p.rating.rate >= it } }
        list = if(ascending == true) list.sortedBy { it.price } else list.sortedByDescending { it.price }
        emit(list)
    }

}