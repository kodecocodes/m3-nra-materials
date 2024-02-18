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
    // TODO: Implement with HttpsUrlConnection
  }

  suspend fun loginUser(username: String, password: String, onResponse: (Throwable?) -> Unit) {
    // TODO: Implement this with Retrofit
  }

  suspend fun getMovies(onResponse: (List<MovieReview>?, Throwable?) -> Unit) {
    // TODO: Implement this with Retrofit
  }

  fun getProfile(onResponse: (User?, Throwable?) -> Unit) {
    // TODO: Implement in the last lesson with auth.
  }

  fun postReview(movieReview: MovieReview, onResponse: (MovieReview?, Throwable?) -> Unit) {
    // TODO: Implement in the last lesson with auth.
  }
}
