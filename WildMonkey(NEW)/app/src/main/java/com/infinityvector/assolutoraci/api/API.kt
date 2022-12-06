package com.infinityvector.assolutoraci.api

import com.infinityvector.assolutoraci.data.Country
import com.infinityvector.assolutoraci.data.Geo
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("json/?key=LbwKKoO9eF4GLMz")
    suspend fun getData(): Response<Country>

    @GET("const.json")
    suspend fun getDataDev(): Response<Geo>

}