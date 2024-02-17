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
fun HomeScreen(
  movieDiaryApi: MovieDiaryApi,
  onProfileTapped: () -> Unit,
) {
  val screenScope = rememberCoroutineScope()
  val scaffoldState = rememberScaffoldState()
  var openDialog by remember { mutableStateOf(false) }
  var movieReviewList by remember { mutableStateOf<List<MovieReview>>(emptyList()) }

  LaunchedEffect(Unit) {
    // TODO: Implement getMovies here
  }

  if (openDialog) {
    NewEntryDialog(
      onDismissRequest = { openDialog = false },
      onConfirmation = {// TODO: Implement post new entry
      }
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
