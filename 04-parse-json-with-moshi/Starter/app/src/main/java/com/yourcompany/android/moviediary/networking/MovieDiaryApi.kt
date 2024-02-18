/*
 * Copyright (c) 2024 Kodeco Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
