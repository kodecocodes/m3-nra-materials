package com.yourcompany.android.moviediary.networking

import com.yourcompany.android.moviediary.model.MovieReview
import com.yourcompany.android.moviediary.model.User
import com.yourcompany.android.moviediary.model.request.RegisterBody
import com.yourcompany.android.moviediary.model.response.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MovieDiaryApiService {

  @POST("user/register")
  suspend fun registerUser(@Body body: RegisterBody): Response<Unit>

  @POST("user/login")
  suspend fun loginUser(@Body registerBody: RegisterBody): LoginResponse

  @GET("movies")
  suspend fun getMovies(): List<MovieReview>

  @GET("user")
  suspend fun getProfile(): User

  @POST("movies")
  suspend fun postReview(@Body movieReview: MovieReview): MovieReview
}
