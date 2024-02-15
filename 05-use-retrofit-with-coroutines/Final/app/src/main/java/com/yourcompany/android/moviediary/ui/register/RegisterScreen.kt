package com.yourcompany.android.moviediary.ui.register

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.yourcompany.android.moviediary.model.User
import com.yourcompany.android.moviediary.networking.ConnectivityChecker
import com.yourcompany.android.moviediary.networking.MovieDiaryApi
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
  movieDiaryApi: MovieDiaryApi,
  connectivityChecker: ConnectivityChecker,
  onUserRegistered: () -> Unit = {},
  onLoginTapped: () -> Unit,
) {
  val scaffoldState = rememberScaffoldState()
  val screenScope = rememberCoroutineScope()
  val focusManager = LocalFocusManager.current
  val context = LocalContext.current

  var username by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
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
        value = email,
        onValueChange = { email = it },
        label = { Text(text = "Email") },
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
          if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            if (connectivityChecker.hasNetworkConnection()) {
              val result = movieDiaryApi.registerUser(username, email, password)
              result.onSuccess {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                onUserRegistered()
              }
                .onFailure { scaffoldState.snackbarHostState.showSnackbar(it.message ?: "") }
            } else {
              scaffoldState.snackbarHostState.showSnackbar("Check your network connection.")
            }
          } else {
            scaffoldState.snackbarHostState.showSnackbar("Please fill in all the fields.")
          }
        }
      }) {
        Text(text = "Register")
      }

      Spacer(modifier = Modifier.size(40.dp))

      Text(text = "Already have an account?")

      TextButton(onClick = { onLoginTapped() }) {
        Text("Login")
      }
    }
  }
}
