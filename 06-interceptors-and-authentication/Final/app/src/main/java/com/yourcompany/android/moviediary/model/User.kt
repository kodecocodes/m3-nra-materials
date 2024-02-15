package com.yourcompany.android.moviediary.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
  val username: String,
  val email: String,
)
