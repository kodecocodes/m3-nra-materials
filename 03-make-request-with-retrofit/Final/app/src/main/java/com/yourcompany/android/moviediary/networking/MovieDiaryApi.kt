package com.yourcompany.android.moviediary.networking

import com.yourcompany.android.moviediary.model.MovieReview
import com.yourcompany.android.moviediary.model.User
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
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
    val jsonBody = JSONObject().apply {
      put("username", username)
      put("email", email)
      put("password", password)
    }.toString()

    val body = RequestBody.create(MediaType.parse("application/json"), jsonBody)

    apiService.registerUser(body).enqueue(object : Callback<ResponseBody> {
      override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        val message = response.body()?.string()
        if (message == null) {
          val error = response.errorBody()?.string()
          onResponse(null, Throwable(error))
          return
        } else {
          onResponse(message, null)
        }
      }

      override fun onFailure(call: Call<ResponseBody>, error: Throwable) {
        onResponse(null, error)
      }
    })
  }

  fun loginUser(username: String, password: String, onResponse: (String?, Throwable?) -> Unit) {
    val user = JSONObject().apply {
      put("username", username)
      put("password", password)
    }.toString()

    val body = RequestBody.create(MediaType.parse("application/json"), user)

    apiService.loginUser(body)
      .enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
          val message = response.body()?.string()
          if (message == null) {
            val error = response.errorBody()?.string()
            onResponse(null, Throwable(error))
            return
          } else {
            onResponse(message, null)
          }
        }

        override fun onFailure(call: Call<ResponseBody>, error: Throwable) {
          onResponse(null, error)
        }
      })
  }

  fun getMovies(onResponse: (List<MovieReview>?, Throwable?) -> Unit) {
    apiService.getMovies().enqueue(object : Callback<ResponseBody> {
      override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        if (response.isSuccessful) {

          val movieReviews = mutableListOf<MovieReview>()
          JSONArray(response.body()?.string()).also {
            for (i in 0 until it.length())
              it.getJSONObject(i).run {
                MovieReview(
                  title = getString("title"),
                  description = getString("description"),
                  comment = getString("comment"),
                ).also { movieReviews.add(it) }
              }
          }

          onResponse(movieReviews, null)
        } else {
          onResponse(null, Throwable(response.errorBody()?.string()))
        }
      }

      override fun onFailure(call: Call<ResponseBody>, error: Throwable) {
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
