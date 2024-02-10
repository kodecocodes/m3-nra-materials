package com.yourcompany.android.moviediary.networking

import com.yourcompany.android.moviediary.App
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class ApiAuthenticator : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request {
    val token = App.getUserToken()
    return response.request.newBuilder()
      .addHeader("Authorization", "Bearer $token")
      .build()
  }
}
