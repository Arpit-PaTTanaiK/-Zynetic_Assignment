package com.example.zynetictask.ui.productdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductDetailsScreen(productId: Int, viewModel: ProductDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val state by viewModel.productDetails.collectAsState()

    LaunchedEffect(productId) {
        viewModel.fetchProductDetails(productId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.error != null -> {
                Text("Error: ${state.error}", modifier = Modifier.align(Alignment.Center))
            }

            state.product != null -> {
                val product = state.product!!
                Column(modifier = Modifier.padding(16.dp)) {
                    val pagerState = rememberPagerState(pageCount = {
                        product.images.size
                    })
                    HorizontalPager(state = pagerState) { page ->
                        Image(
                            painter = rememberAsyncImagePainter(product.images[page]),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("TITLE: ${product.title}", modifier = Modifier.padding(vertical = 4.dp))
                    Text("PRICE: ${product.price}", modifier = Modifier.padding(vertical = 4.dp))
                    Text("⭐ ${product.rating}", modifier = Modifier.padding(vertical = 4.dp))
                    Text("CATEGORY: ${product.category}", modifier = Modifier.padding(vertical = 4.dp))
                    Text("DESCRIPTION: ${product.description}", modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}
