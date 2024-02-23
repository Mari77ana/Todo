package com.example.todo.api

import retrofit2.Call
import retrofit2.http.GET

interface FoxApi {

  @GET("/floof/")
  suspend fun getFoxInfo(): Call<FoxData>
}