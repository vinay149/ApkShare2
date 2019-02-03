package com.myapp.vinay.apkshare

import okhttp3.Call
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface getDataService {

    @GET("/my_test_schema.json?key=b2b153a0")
    fun getAllData():retrofit2.Call<List<RetroData>>

    @POST("/save")
    fun getToken(@Body requestBody: RequestBody?):retrofit2.Call<TokenId>

}