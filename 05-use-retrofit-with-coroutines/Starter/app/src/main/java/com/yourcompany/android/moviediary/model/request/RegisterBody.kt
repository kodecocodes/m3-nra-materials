package com.yourcompany.android.moviediary.model.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterBody(
  val username: String,
  val email: String? = null,
  val password: String,
)
