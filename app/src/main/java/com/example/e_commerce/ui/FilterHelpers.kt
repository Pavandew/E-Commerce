package com.example.e_commerce.ui

import com.example.e_commerce.model.Rating
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private var filterJob: Job? = null
fun applyFilterDebounced (
    vm: ProductViewModel,
    category: String, priceMax: Float,
    rating: Float,
    ascending: Boolean
){
    filterJob?.cancel()
    filterJob = CoroutineScope(Dispatchers.Main).launch {
        delay(300)
        val cat = if (category == "All") null else category
        val maxP = if(priceMax.toInt() == 0) null else priceMax.toDouble()
        val minR = if(rating == 0f) null else rating.toDouble()
        vm.setFilter(ProductFilter(category = cat, minPrice = null, maxPrice = maxP, minRating = minR, ascending = ascending))
    }
}