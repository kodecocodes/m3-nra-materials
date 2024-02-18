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
