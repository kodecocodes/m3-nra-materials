package com.yourcompany.android.moviediary.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieReview(val title: String, val description: String, val comment: String)

