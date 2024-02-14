package com.yourcompany.android.moviediary.networking

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://http-api-93211a10efe2.herokuapp.com/"

private fun buildClient(): OkHttpClient = OkHttpClient.Builder().build()

private fun buildRetrofit(): Retrofit = Retrofit.Builder()
  .baseUrl(BASE_URL)
  .client(buildClient())
  .addConverterFactory(MoshiConverterFactory.create().asLenient())
  .build()

fun buildMoshi(): Moshi = Moshi.Builder().build()

fun buildMovieDiaryService(): MovieDiaryApiService = buildRetrofit().create(MovieDiaryApiService::class.java)
