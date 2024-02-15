package com.yourcompany.android.moviediary.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
  val username: String,
  val email: String,
  val token: String,
)
