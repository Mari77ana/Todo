package com.example.todo.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoxRepository {
    private val urlAddress = "https://randomfox.ca/"
    val retrofit = Retrofit.Builder()
        .baseUrl(urlAddress)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    // single instance of Retrofit and FoxApi  that can be accessed throughout the application
    val foxApi = retrofit.create(FoxApi::class.java)



    // Ingen :Call<FoxData> det är en Callback
    // Med suspend använd bara FoxData
    suspend fun getFox(): FoxData {
        return foxApi.getFoxImages()
    }






}