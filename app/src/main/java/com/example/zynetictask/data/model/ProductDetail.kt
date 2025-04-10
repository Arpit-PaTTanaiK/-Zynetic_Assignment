package com.example.zynetictask.data.model

data class ProductDetail(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val images: List<String>
)