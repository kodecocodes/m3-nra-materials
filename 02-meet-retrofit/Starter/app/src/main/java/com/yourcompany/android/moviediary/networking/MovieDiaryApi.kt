package com.yourcompany.android.moviediary.networking

import com.yourcompany.android.moviediary.model.MovieReview
import com.yourcompany.android.moviediary.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
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

      val connection = URL("https://http-api-93211a10efe2.herokuapp.com/user/register").openConnection() as HttpsURLConnection
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

  suspend fun getMovies(): List<MovieReview> {
    return emptyList()
  }

  suspend fun getMe(): User {
    return User("")
  }
}
