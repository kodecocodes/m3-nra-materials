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

package com.yourcompany.android.moviediary.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.yourcompany.android.moviediary.networking.MovieDiaryApi
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
  movieDiaryApi: MovieDiaryApi,
  onLogin: (String) -> Unit,
  onRegisterTapped: () -> Unit,
) {
  val scaffoldState = rememberScaffoldState()
  val screenScope = rememberCoroutineScope()
  val focusManager = LocalFocusManager.current

  var username by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }


  Scaffold(scaffoldState = scaffoldState) { innerPadding ->
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    ) {
      OutlinedTextField(
        modifier = Modifier.padding(8.dp),
        value = username,
        onValueChange = { username = it },
        label = { Text("Username") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
      )
      OutlinedTextField(
        modifier = Modifier.padding(8.dp),
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
      )
      Button(onClick = {
        focusManager.clearFocus()
        screenScope.launch {
          if (username.isNotBlank() && password.isNotBlank()) {
            movieDiaryApi.loginUser(username, password) { token, error ->
              if (token == null) {
                screenScope.launch {
                  scaffoldState.snackbarHostState.showSnackbar(error?.message ?: "")
                }
              } else {
                onLogin(token)
              }
            }
          } else {
            scaffoldState.snackbarHostState.showSnackbar("Check your network connection.")
          }
        }
      }) {
        Text(text = "Login")
      }

      Spacer(modifier = Modifier.size(40.dp))

      Text(text = "Don't have an account yet?")

      TextButton(onClick = { onRegisterTapped() }) {
        Text("Register")
      }
    }
  }
}
