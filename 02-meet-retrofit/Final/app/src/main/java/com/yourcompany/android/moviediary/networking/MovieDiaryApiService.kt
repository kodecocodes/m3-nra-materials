package com.yourcompany.android.moviediary.networking

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MovieDiaryApiService {

  @POST("user/register")
  fun registerUser()

  @POST("user/login")
  fun loginUser()

  @GET("user")
  fun getProfile()

  @GET("movies")
  fun getMovies()

  @PUT("movies")
  fun makeEntry()
}
