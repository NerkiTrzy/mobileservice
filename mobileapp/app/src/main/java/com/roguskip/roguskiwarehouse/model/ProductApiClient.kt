package com.roguskip.roguskiwarehouse.model

import io.reactivex.Completable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiClient {
    @POST("manufacturers/{manufacturerId}/products") fun addProduct(@Path("manufacturerId") id: Int,
                                                                    @Body product: Product): Completable
    @POST("products/{productUuid}/change-quantity") fun changeQuantity(@Path("productUuid")productUuid: String, @Body quantity: Int) : Completable
    @DELETE("products/{productUuid}") fun deleteProduct(@Path("productUuid") productUuid: String) : Completable
    @POST("operations/do-operations") fun doOperations(@Body operationList: List<Operation>) : Completable


    companion object {
        fun create(): ProductApiClient {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080/api/")
                .build()

            return retrofit.create(ProductApiClient::class.java)
        }
    }
}