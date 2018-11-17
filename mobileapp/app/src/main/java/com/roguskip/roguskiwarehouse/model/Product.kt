package com.roguskip.roguskiwarehouse.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Product(val manufacturerName : String, val productName : String, val manufacutrerId: Int,
                   val price : BigDecimal, val quantity : Int, val productId: Int, val currency: String)
data class ProductList(
    @SerializedName("products")
    val products: List<Product>
)