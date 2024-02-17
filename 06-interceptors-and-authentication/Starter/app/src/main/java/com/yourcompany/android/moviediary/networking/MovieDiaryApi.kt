package com.yourcompany.android.moviediary.networking

import com.yourcompany.android.moviediary.model.MovieReview
import com.yourcompany.android.moviediary.model.User
import com.yourcompany.android.moviediary.model.request.RegisterBody
import com.yourcompany.android.moviediary.model.response.LoginResponse

class MovieDiaryApi(private val apiService: MovieDiaryApiService) {

  suspend fun registerUser(
    username: String,
    email: String,
    password: String,
  ): Result<String> = try {
    val body = RegisterBody(username, email, password)
    val result = apiService.registerUser(body)
    if (result.isSuccessful) Result.success(result.message()) else Result.failure(
      Throwable(
        result.errorBody()?.string()
      )
    )
  } catch (error: Throwable) {
    Result.failure(error)
  }

  suspend fun loginUser(
    username: String,
    password: String,
  ): Result<LoginResponse> {
    return runCatching { apiService.loginUser(RegisterBody(username = username, password = password)) }
  }

  suspend fun getMovies(): Result<List<MovieReview>> = runCatching { apiService.getMovies() }

  suspend fun getProfile(): Result<User> = runCatching { apiService.getProfile() }

  suspend fun postReview(movieReview: MovieReview): Result<MovieReview> =
    runCatching { apiService.postReview(movieReview) }
}
