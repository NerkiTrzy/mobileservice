package com.roguskip.roguskiwarehouse.model

import java.io.Serializable

data class Operation(val uuid: String, val operationName: OperationName, val productView: ProductView, val quantity: Int) :
    Serializable