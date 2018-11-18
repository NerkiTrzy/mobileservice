package com.roguskip.roguskiwarehouse.model

import java.io.Serializable
import java.math.BigDecimal


data class Product(val manufacturerName : String, val productName : String, val manufacutrerId: Int,
                   val price : BigDecimal, val quantity : Int, val productId: Int, val currency: String) : Serializable
