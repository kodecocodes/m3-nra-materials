package com.yourcompany.android.moviediary

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.core.content.edit

private const val KEY_PREFERENCES = "movie_diary_preferences"
private const val KEY_TOKEN = "token"

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    instance = this
  }

  companion object {
    private lateinit var instance: App

    private val sharedPrefs by lazy { instance.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE) }

    fun saveUserToken(token: String) {
      sharedPrefs.edit { putString(KEY_TOKEN, token) }
    }

    fun getUserToken(): String = sharedPrefs.getString(KEY_TOKEN, "") ?: ""
  }
}
