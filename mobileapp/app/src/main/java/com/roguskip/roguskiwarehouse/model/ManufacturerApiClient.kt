package com.roguskip.roguskiwarehouse.model

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ManufacturerApiClient {
    @GET("manufacturers") fun getManufacturers(): Observable<List<Manufacturer>>

    companion object {
        fun create(): ManufacturerApiClient {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080/api/")
                .build()

            return retrofit.create(ManufacturerApiClient::class.java)
        }
    }
}