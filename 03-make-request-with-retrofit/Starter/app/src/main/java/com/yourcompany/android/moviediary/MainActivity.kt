/*
 * Copyright (c) 2024 Kodeco Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.yourcompany.android.moviediary

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.getSystemService
import com.yourcompany.android.moviediary.networking.ConnectivityChecker
import com.yourcompany.android.moviediary.networking.MovieDiaryApi
import com.yourcompany.android.moviediary.networking.buildMovieDiaryService
import com.yourcompany.android.moviediary.ui.movies.MoviesScreen
import com.yourcompany.android.moviediary.ui.login.LoginScreen
import com.yourcompany.android.moviediary.ui.navigation.Screens
import com.yourcompany.android.moviediary.ui.profile.ProfileScreen
import com.yourcompany.android.moviediary.ui.register.RegisterScreen
import com.yourcompany.android.moviediary.ui.theme.MovieDiaryTheme

class MainActivity : ComponentActivity() {

  private val movieApi by lazy { MovieDiaryApi(buildMovieDiaryService()) }
  private val connectivityManager by lazy { getSystemService<ConnectivityManager>() }
  private val connectivityChecker by lazy { ConnectivityChecker(connectivityManager) }

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)
    setContent {
      var userLoggedIn by remember { mutableStateOf(App.getUserToken().isNotBlank()) }
      var currentScreen by remember { mutableStateOf(if (userLoggedIn) Screens.MOVIES else Screens.LOGIN) }

      MovieDiaryTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          when (currentScreen) {
            Screens.LOGIN -> {
              LoginScreen(
                movieDiaryApi = movieApi,
                connectivityChecker = connectivityChecker,
                onLogin = { token ->
                  App.saveUserToken(token)
                  userLoggedIn = true
                  currentScreen = Screens.MOVIES
                },
                onRegisterTapped = { currentScreen = Screens.REGISTER }
              )
            }

            Screens.REGISTER -> {
              RegisterScreen(
                movieDiaryApi = movieApi,
                connectivityChecker = connectivityChecker,
                onUserRegistered = { currentScreen = Screens.LOGIN },
                onLoginTapped = { currentScreen = Screens.LOGIN })
            }

            Screens.MOVIES -> {
              MoviesScreen(
                movieDiaryApi = movieApi,
                onProfileTapped = { currentScreen = Screens.PROFILE },
              )
            }

            Screens.PROFILE -> {
              ProfileScreen(
                movieDiaryApi = movieApi,
                onBack = { currentScreen = Screens.MOVIES },
                onLogout = {
                  App.saveUserToken("")
                  userLoggedIn = false
                  currentScreen = Screens.LOGIN })
            }
          }
        }
      }
    }
  }
}
