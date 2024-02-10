package com.yourcompany.android.moviediary.networking

import com.squareup.moshi.Moshi
import com.yourcompany.android.moviediary.BuildConfig
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "http://10.0.2.2:8080/"

private fun buildClient(): OkHttpClient = OkHttpClient.Builder()
  .addInterceptor(buildLoggingInterceptor())
  .authenticator(ApiAuthenticator())
  .build()

private fun buildRetrofit(): Retrofit = Retrofit.Builder()
  .baseUrl(BASE_URL)
  .client(buildClient())
  .addConverterFactory(MoshiConverterFactory.create().asLenient())
  .build()

private fun buildLoggingInterceptor() = HttpLoggingInterceptor()
  .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

fun buildMoshi(): Moshi = Moshi.Builder().build()

fun buildMovieDiaryService(): MovieDiaryApiService = buildRetrofit().create(MovieDiaryApiService::class.java)
