package com.roguskip.roguskiwarehouse.model

import java.io.Serializable
import java.math.BigDecimal

data class Product(val name: String, val id: Int, val currency: String, val price: BigDecimal, val quantity: Int, val uuid: String):
    Serializable