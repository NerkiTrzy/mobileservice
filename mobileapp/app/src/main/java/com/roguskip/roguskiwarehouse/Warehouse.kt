package com.roguskip.roguskiwarehouse

import java.io.Serializable
import java.math.BigDecimal

data class Warehouse(val id: Int, val name: String, val extraPrice: BigDecimal) : Serializable