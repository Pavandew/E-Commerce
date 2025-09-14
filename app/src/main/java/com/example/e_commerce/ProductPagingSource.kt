package com.example.e_commerce

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.e_commerce.Api.ProductApi
import com.example.e_commerce.model.Product
import kotlin.math.min

class ProductPagingSource(
    private val api: ProductApi,
    private val pageSize: Int = 10
) : PagingSource<Int, Product>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val page = params.key ?: 1
        return try {
            val all = api.getAll()
            val from = (page - 1)  * pageSize
            val to = min(from + pageSize, all.size)

            if(from >= all.size) {
                LoadResult.Page(emptyList(), prevKey = if(page == 1) null else page -1, nextKey = null)

            } else {
                val slice = all.subList(from, to)
                LoadResult.Page(slice, prevKey = if(page == 1) null else page -1, nextKey = if(to < all.size) page+1 else null)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}