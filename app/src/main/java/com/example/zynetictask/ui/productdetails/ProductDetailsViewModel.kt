package com.example.zynetictask.ui.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zynetictask.data.model.ProductDetail
import com.example.zynetictask.data.network.productNetworkServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {

    private val detailOfProduct = MutableStateFlow(ProductDetailsState())
    val productDetails: StateFlow<ProductDetailsState> = detailOfProduct

    fun fetchProductDetails(id: Int) {
        viewModelScope.launch {
            detailOfProduct.value = detailOfProduct.value.copy(loading = true, error = null)
            try {
                val product = productNetworkServices.getProductById(id)
                detailOfProduct.value = ProductDetailsState(product = product)
            } catch (e: Exception) {
                detailOfProduct.value = ProductDetailsState(error = "Failed to load product: ${e.message}")
            }
        }
    }

    data class ProductDetailsState(
        val loading: Boolean = false,
        val product: ProductDetail? = null,
        val error: String? = null
    )
}
