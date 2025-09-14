package com.example.e_commerce.data.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room entity representing an item in the cart.
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int = 1   // by default
)
