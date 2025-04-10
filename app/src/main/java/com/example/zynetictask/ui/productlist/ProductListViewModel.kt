package com.example.zynetictask.ui.productlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zynetictask.data.model.Product
import com.example.zynetictask.data.network.productNetworkServices
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {

    private val productState = mutableStateOf(Productstate())
    val productssState: State<Productstate> = productState

    private var skip = 0
    private val limit = 10
    private var isFetchingMore = false

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        if (isFetchingMore || (productState.value.list.size >= productState.value.total)) return

        isFetchingMore = true
        productState.value = productState.value.copy(loading = true)

        viewModelScope.launch {
            try {
                val response = productNetworkServices.getProducts(skip, limit)
                productState.value = productState.value.copy(
                    list = productState.value.list + response.products,
                    loading = false,
                    error = null,
                    total = response.total
                )
                skip += limit
            } catch (e: Exception) {
                productState.value = productState.value.copy(
                    loading = false,
                    error = "Error ${e.message}"
                )
            } finally {
                isFetchingMore = false
            }
        }
    }

    data class Productstate(
        val loading: Boolean = false,
        val list: List<Product> = emptyList(),
        val error: String? = null,
        val total: Int = Int.MAX_VALUE
    )
}

