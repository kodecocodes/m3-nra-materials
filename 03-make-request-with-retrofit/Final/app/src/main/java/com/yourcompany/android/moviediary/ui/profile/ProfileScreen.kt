package com.yourcompany.android.moviediary.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.yourcompany.android.moviediary.networking.MovieDiaryApi
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
  movieDiaryApi: MovieDiaryApi,
  onBack: () -> Unit,
  onLogout: () -> Unit,
) {
  val screenScope = rememberCoroutineScope()
  var username by remember { mutableStateOf("") }
  LaunchedEffect(Unit) {
    movieDiaryApi.getProfile { user, throwable ->
      username = user?.username ?: ""
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
      Text(text = username)
    }
  }
}
