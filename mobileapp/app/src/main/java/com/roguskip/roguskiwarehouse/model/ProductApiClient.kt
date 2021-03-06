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
    @POST("products/{productId}/change-quantity") fun changeQuantity(@Path("productId")id: Int, @Body quantity: Int) : Completable
    @DELETE("products/{productId}") fun deleteProduct(@Path("productId") id: Int) : Completable

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