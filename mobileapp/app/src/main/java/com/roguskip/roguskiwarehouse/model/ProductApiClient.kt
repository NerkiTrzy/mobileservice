package com.roguskip.roguskiwarehouse.model

import retrofit2.Retrofit
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ProductApiClient {
    @GET("products") fun getProducts(): Observable<ProductEmbedded>

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