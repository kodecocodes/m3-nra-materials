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

package com.yourcompany.android.moviediary.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourcompany.android.moviediary.model.User
import com.yourcompany.android.moviediary.networking.MovieDiaryApi
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
  movieDiaryApi: MovieDiaryApi,
  onBack: () -> Unit,
  onLogout: () -> Unit,
) {

  val screenScope = rememberCoroutineScope()
  val scaffoldState = rememberScaffoldState()
  var user by remember { mutableStateOf(User("", "")) }

  LaunchedEffect(Unit) {
    movieDiaryApi.getProfile()
      .onSuccess { user = it }
      .onFailure { scaffoldState.snackbarHostState.showSnackbar(it.message ?: "") }
  }

  Scaffold(topBar = {
    TopAppBar(
      title = {
        Text(text = "MovieDiary")
      },
      navigationIcon = {
        IconButton(onClick = { onBack() }) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back")
        }
      },
      actions = {
        IconButton(
          onClick = {
            screenScope.launch { onLogout() }
          }) {
          Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Profile icon")
        }
      }
    )
  }
  ) { paddingValues ->
    Column(Modifier.padding(paddingValues)) {
      Card(
        Modifier
          .padding(12.dp)
          .fillMaxWidth()
      ) {
        Column(Modifier.padding(20.dp)) {
          Text(text = user.username)
          Text(text = user.email)
        }
      }
    }
  }
}
