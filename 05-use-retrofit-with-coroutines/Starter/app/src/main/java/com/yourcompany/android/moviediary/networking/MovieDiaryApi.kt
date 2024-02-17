package com.yourcompany.android.moviediary.networking

import com.yourcompany.android.moviediary.model.MovieReview
import com.yourcompany.android.moviediary.model.User
import com.yourcompany.android.moviediary.model.request.RegisterBody
import com.yourcompany.android.moviediary.model.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDiaryApi(private val apiService: MovieDiaryApiService) {

  fun registerUser(
    username: String,
    email: String,
    password: String,
    onResponse: (String?, Throwable?) -> Unit,
  ) {
    val body = RegisterBody(username, email, password)

    apiService.registerUser(body).enqueue(object : Callback<Unit> {
      override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
        if (response.isSuccessful) {
          onResponse("Success", null)
        } else {
          onResponse(null, Throwable(response.message()))
        }
      }

      override fun onFailure(call: Call<Unit>, error: Throwable) {
        onResponse(null, error)
      }
    })
  }

  fun loginUser(username: String, password: String, onResponse: (LoginResponse?, Throwable?) -> Unit) {
    apiService.loginUser(RegisterBody(username = username, password = password))
      .enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
          if (response.isSuccessful) {
            onResponse(response.body(), null)
          } else {
            onResponse(null, Throwable(response.errorBody()?.string()))
          }
        }

        override fun onFailure(call: Call<LoginResponse>, error: Throwable) {
          onResponse(null, error)
        }
      })
  }

  fun getMovies(onResponse: (List<MovieReview>?, Throwable?) -> Unit) {
    apiService.getMovies().enqueue(object : Callback<List<MovieReview>> {
      override fun onResponse(call: Call<List<MovieReview>>, response: Response<List<MovieReview>>) {
        if (response.isSuccessful) {
          onResponse(response.body(), null)
        } else {
          onResponse(null, Throwable(response.errorBody()?.string()))
        }
      }

      override fun onFailure(call: Call<List<MovieReview>>, error: Throwable) {
        onResponse(null, error)
      }
    })
  }

  fun getProfile(onResponse: (User?, Throwable?) -> Unit) {
    // TODO: Implement in the last lesson with auth.
  }

  fun postReview(movieReview: MovieReview, onResponse: (MovieReview?, Throwable?) -> Unit) {
    // TODO: Implement in the last lesson with auth.
  }
}
