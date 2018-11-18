package com.roguskip.roguskiwarehouse.model

import java.io.Serializable
import java.math.BigDecimal


data class ProductView(val manufacturerName : String, val productName : String, var manufacturerId: Int,
                       val price : BigDecimal, val quantity : Int, val productId: Int, val currency: String) : Serializable
