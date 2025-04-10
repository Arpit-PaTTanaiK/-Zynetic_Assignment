package com.example.zynetictask.data.model

data class ProductRes(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)