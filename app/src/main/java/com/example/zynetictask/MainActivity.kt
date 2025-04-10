package com.example.zynetictask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.zynetictask.ui.productdetails.ProductDetailsScreen
import com.example.zynetictask.ui.productlist.ProductListScreen
import com.example.zynetictask.ui.productlist.ProductListViewModel
import com.example.zynetictask.ui.theme.ZyneticTaskTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ProductListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ZyneticTaskTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "product_list"
                ) {
                    composable("product_list") {
                        ProductListScreen(
                            viewstate = viewModel.productssState.value,
                            navToDetails = { product ->
                                navController.navigate("product_details/${product.id}")
                            },
                            loadMore = {
                                viewModel.fetchProducts()
                            }
                        )
                    }

                    composable(
                        "product_details/{productId}",
                        arguments = listOf(navArgument("productId") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                        ProductDetailsScreen(productId = productId)
                    }
                }
            }
        }
    }
}
