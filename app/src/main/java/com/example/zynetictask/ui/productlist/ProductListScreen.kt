package com.example.zynetictask.ui.productlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.zynetictask.data.model.Product

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewstate: ProductListViewModel.Productstate,
    navToDetails: (Product) -> Unit,
    loadMore: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewstate.loading && viewstate.list.isEmpty() -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            viewstate.error != null -> {
                Text(text = "Check Your Internet Connection!!", modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                CategoryScreen(
                    products = viewstate.list,
                    navgatetoDetaillsss = navToDetails,
                    loadMore = loadMore,
                    MoreLoading = viewstate.loading
                )
            }
        }
    }
}

@Composable
fun CategoryScreen(
    products: List<Product>,
    navgatetoDetaillsss: (Product) -> Unit,
    loadMore: () -> Unit,
    MoreLoading: Boolean
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = gridState,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) { product ->
            productItem(product = product, navgatetoDetaillsss)
        }

        if (MoreLoading) {
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }


    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVis = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVis >= products.size - 4
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            loadMore()
        }
    }
}

@Composable
fun productItem(product: Product, navToDettails: (Product) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { navToDettails(product) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = product.title,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )


        Image(
            painter = rememberAsyncImagePainter(product.images.firstOrNull()),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )


        Text(
            text = product.description,
            color = Color.DarkGray,
            style = TextStyle(fontWeight = FontWeight.Normal),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

