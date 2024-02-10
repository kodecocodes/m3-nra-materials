package com.yourcompany.android.moviediary.networking

import com.yourcompany.android.moviediary.model.MovieReview
import com.yourcompany.android.moviediary.model.User

class MovieDiaryApi {

  suspend fun registerUser(
    username: String,
    email: String,
    password: String,
    onUserRegistered: (String?, Throwable?) -> Unit,
  ) {
    // TODO: Implement with HttpUrlConnection
  }

  suspend fun loginUser(username: String, password: String, onResponse: (String?, Throwable?) -> Unit) {
    // TODO: Implement this with Retrofit
  }

  suspend fun getMovies(): List<MovieReview> {
    return emptyList()
  }

  suspend fun getProfile(): User {
    return User("", "")
  }
}
