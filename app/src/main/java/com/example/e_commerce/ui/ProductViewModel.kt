package com.example.e_commerce.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.e_commerce.ProductPagingSource
import com.example.e_commerce.ProductRepository
import com.example.e_commerce.data.cart.AppDatabase
import com.example.e_commerce.data.cart.CartItem
import com.example.e_commerce.model.Product
import com.example.e_commerce.service.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val api = RetrofitClient.api
    private val db = AppDatabase.getInstance(application)
    private val repo = ProductRepository(api, db.cartDao())

    // Pagination - Paging2 with slicing FakeStore data
    val pager = Pager(PagingConfig(pageSize = 10)) { ProductPagingSource(api, 10) }
    val productsPagingFlow = pager.flow.cachedIn(viewModelScope)

    // Filters - users changes
    private val _filter = MutableStateFlow(ProductFilter())
    val filter: StateFlow<ProductFilter> = _filter.asStateFlow()

    // Debounced filtered ( non-paged) results derived from repo.filterProducts
    private val _filtered = _filter
        .debounce(300)
        .flatMapLatest { f ->
            flow {
                val all = repo.fetchAll()
                emitAll(repo.filterProducts(all, f.category, f.minPrice, f.maxPrice, f.minRating,f.ascending))
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filteredProducts : StateFlow<List<Product>> = _filtered

    // cart flows
    val cartItems: Flow<List<CartItem>> = repo.cartFlow()
    val cartTotal: Flow<Double?> = repo.cartTotal()

    // categories loaded once
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _categories.value = repo.getCategories()
            } catch (_: Exception) {}
        }
    }

    fun setFilter(f: ProductFilter) { _filter.value = f }

    fun addToCart(product: Product) = viewModelScope.launch (Dispatchers.IO){
        val item = CartItem(productId = product.id, title = product.title, price = product.price, image = product.image, quantity = 1)
        repo.addToCart(item)
    }

    fun removeFromCart(id: Int)  = viewModelScope.launch (Dispatchers.IO){
        repo.removeToCart(id)
    }

}