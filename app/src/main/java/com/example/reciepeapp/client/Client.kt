package com.example.reciepeapp.client

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

    val gson = GsonBuilder().create()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api = retrofit.create(getResult::class.java)
}
