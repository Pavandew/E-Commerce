package com.example.e_commerce.Api

import com.example.e_commerce.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getAll(): List<Product>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/categories/{category}")
    suspend fun getByCategory(@Path("category") category: String): List<Product>
}