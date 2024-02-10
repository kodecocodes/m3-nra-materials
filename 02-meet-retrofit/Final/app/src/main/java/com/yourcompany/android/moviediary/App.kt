package com.yourcompany.android.moviediary

import android.app.Application

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    instance = this

  }

  fun saveUserToken(token: String) {

  }

  fun getUserToken(): String {
    return ""
  }

  companion object {
    lateinit var instance: Application
  }
}
