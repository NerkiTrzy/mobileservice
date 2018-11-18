package com.roguskip.roguskiwarehouse.model

import io.reactivex.Completable
import retrofit2.Retrofit
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ProductViewApiClient {
    @GET("products") fun getProducts(): Observable<List<ProductView>>
    @POST("manufacturers/{manufacturerId}/products") fun addProduct(@Path("manufacturerId") id: Int,
                                                                    @Body productView: ProductView): Completable

    companion object {
        fun create(): ProductViewApiClient {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080/api/")
                .build()

            return retrofit.create(ProductViewApiClient::class.java)
        }
    }
}