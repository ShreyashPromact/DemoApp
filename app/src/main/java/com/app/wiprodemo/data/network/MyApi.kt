package com.app.wiprodemo.data.network

import com.app.wiprodemo.data.db.entities.Post
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyApi {

    @GET("facts.json")
    suspend fun getPosts() : Response<List<Post>>

    companion object{
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor) : MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}