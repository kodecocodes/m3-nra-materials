package com.yourcompany.android.moviediary.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(val token: String)
