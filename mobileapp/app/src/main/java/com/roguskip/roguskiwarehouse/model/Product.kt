package com.roguskip.roguskiwarehouse.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Product(val manufacturerName : String, val name : String,
                   val price : BigDecimal, val quantity : Int)
data class ProductList(
    @SerializedName("products")
    val products: List<Product>
)
data class ProductEmbedded(
    @SerializedName("_embedded")
    val list: ProductList
)