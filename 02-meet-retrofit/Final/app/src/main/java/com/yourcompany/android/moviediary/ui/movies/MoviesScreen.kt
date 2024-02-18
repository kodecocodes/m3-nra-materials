package com.yourcompany.android.moviediary.ui.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yourcompany.android.moviediary.model.MovieReview
import com.yourcompany.android.moviediary.networking.MovieDiaryApi
import kotlinx.coroutines.launch

@Composable
fun MoviesScreen(
  movieDiaryApi: MovieDiaryApi,
  onProfileTapped: () -> Unit,
) {
  val screenScope = rememberCoroutineScope()
  val scaffoldState = rememberScaffoldState()
  var openDialog by remember { mutableStateOf(false) }
  var movieReviewList by remember { mutableStateOf<List<MovieReview>>(emptyList()) }

  LaunchedEffect(Unit) {
    movieDiaryApi.getMovies { movies, throwable ->
      if (!movies.isNullOrEmpty()) {
        movieReviewList = movies
      }
    }
  }

  if (openDialog) {
    NewEntryDialog(
      onDismissRequest = { openDialog = false },
      onConfirmation = { movieReview ->
        movieDiaryApi.postReview(movieReview, onResponse = { newReview, error ->
          if (newReview != null) {
            val newList = movieReviewList.toMutableList()
            newList.add(newReview)
            movieReviewList = newList
          } else {
            screenScope.launch {
              scaffoldState.snackbarHostState.showSnackbar(error?.message ?: "")
            }
          }
        })
        openDialog = false
      },
    )
  }
  Scaffold(topBar = {
    TopAppBar(title = {
      Text(text = "MovieDiary")
    }, actions = {
      IconButton(onClick = {
        screenScope.launch { onProfileTapped() }
      }) {
        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile icon")
      }
    })
  }, floatingActionButton = {
    FloatingActionButton(onClick = { openDialog = true }) {
      Icon(imageVector = Icons.Default.Add, contentDescription = "Add new entry")
    }
  }) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues)) {
      LazyColumn(content = {
        items(movieReviewList.size) { index ->
          Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
            MovieItem(movieReviewList[index])
          }
        }
      })
    }
  }
}
