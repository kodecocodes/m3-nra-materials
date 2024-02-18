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
    movieDiaryApi.getProfile { userResponse, throwable ->
      if (userResponse != null) {
        user = userResponse
      } else {
        screenScope.launch {
          scaffoldState.snackbarHostState.showSnackbar(throwable?.message ?: "")
        }
      }
    }
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
