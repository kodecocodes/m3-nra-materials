package com.yourcompany.android.moviediary.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit

private const val BASE_URL = ""

private fun buildClient(): OkHttpClient = OkHttpClient.Builder().build()

private fun buildRetrofit(): Retrofit = Retrofit.Builder()
  .baseUrl(BASE_URL)
  .client(buildClient())
  .build()

fun buildMovieDiaryService(): MovieDiaryApiService = buildRetrofit().create(MovieDiaryApiService::class.java)
