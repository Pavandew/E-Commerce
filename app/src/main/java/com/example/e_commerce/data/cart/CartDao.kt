package com.example.e_commerce.data.cart

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// DAO exposes flow for UI to observe cart and total price
@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAll(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Query("DELETE FROM cart_items WHERE productId = :id")
    suspend fun remove(id: Int)

    @Query("SELECT SUM(price * quantity) FROM cart_items")
    fun totalPrice(): Flow<Double?>

}