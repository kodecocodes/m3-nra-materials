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

import com.yourcompany.android.moviediary.model.Movie
import com.yourcompany.android.moviediary.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MovieDiaryApi {

  suspend fun registerUser(
    username: String,
    email: String,
    password: String,
    onUserRegistered: (String?, Throwable?) -> Unit,
  ) =
    withContext(Dispatchers.IO) {
      val jsonUser = JSONObject().apply {
        put("username", username)
        put("email", email)
        put("password", password)
      }

      val connection = URL("http://10.0.2.2:8080/user").openConnection() as HttpsURLConnection
      connection.apply {
        setRequestProperty("Content-Type", "application/json")
        setRequestProperty("Accept", "application/json")
        requestMethod = "POST"
        readTimeout = 10000
        connectTimeout = 10000
        doOutput = true
        doInput = true
      }

      val body = jsonUser.toString().toByteArray()

      try {
        connection.outputStream.use { stream ->
          stream.write(body)
        }

        val reader = InputStreamReader(connection.inputStream)

        reader.use {
          val response = StringBuilder()
          val bufferedReader = BufferedReader(reader)
          bufferedReader.useLines { lines ->
            lines.forEach {
              response.append(it.trim())
            }
          }
          onUserRegistered(response.toString(), null)
        }
      } catch (error: Throwable) {
        onUserRegistered(null, error)
      } finally {
        connection.disconnect()
      }
    }

  suspend fun loginUser(username: String, password: String, onResponse: (Throwable?) -> Unit) {
    // TODO: Implement this with Retrofit
  }

  suspend fun getMovies(): List<Movie> {
    return emptyList()
  }

  suspend fun getMe(): User {
    return User("")
  }
}
