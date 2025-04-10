package com.example.zynetictask.data.network

import com.example.zynetictask.data.model.ProductDetail
import com.example.zynetictask.data.model.ProductRes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val retrofit =Retrofit.Builder().baseUrl("https://dummyjson.com/")
    .addConverterFactory(GsonConverterFactory.create()).build()


val productNetworkServices = retrofit.create(ApiService::class.java)
interface ApiService{
    @GET("products?select=title,description,images")
    suspend fun getProducts(
        @retrofit2.http.Query("limit") limit: Int,
        @retrofit2.http.Query("skip") skip: Int
    ): ProductRes

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDetail

}