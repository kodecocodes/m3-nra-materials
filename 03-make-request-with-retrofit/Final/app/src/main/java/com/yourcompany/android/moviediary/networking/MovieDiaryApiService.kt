package com.yourcompany.android.moviediary.networking

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MovieDiaryApiService {

  @POST("user/register")
  fun registerUser(@Body body: RequestBody): Call<ResponseBody>

  @POST("user/login")
  fun loginUser(@Body body: RequestBody): Call<ResponseBody>

  @GET("user")
  fun getProfile(): Call<ResponseBody>

  @GET("movies")
  fun getMovies(): Call<ResponseBody>

  @POST("movies")
  fun postReview(@Body body: RequestBody): Call<ResponseBody>
}
