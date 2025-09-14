package com.example.e_commerce.ui

import com.example.e_commerce.model.Rating

data class ProductFilter(
    val category: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val minRating: Double? = null,
    val ascending: Boolean? = true,
)
