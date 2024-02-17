package com.yourcompany.android.moviediary.networking

import com.yourcompany.android.moviediary.model.MovieReview
import com.yourcompany.android.moviediary.model.User
import com.yourcompany.android.moviediary.model.request.RegisterBody
import com.yourcompany.android.moviediary.model.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MovieDiaryApiService {

  @POST("user/register")
  fun registerUser(@Body body: RegisterBody): Call<Unit>

  @POST("user/login")
  fun loginUser(@Body registerBody: RegisterBody): Call<LoginResponse>

  @GET("movies")
  fun getMovies(): Call<List<MovieReview>>

  @GET("user")
  fun getProfile(): Call<User>

  @POST("movies")
  fun postReview(@Body movieReview: MovieReview): Call<MovieReview>
}
