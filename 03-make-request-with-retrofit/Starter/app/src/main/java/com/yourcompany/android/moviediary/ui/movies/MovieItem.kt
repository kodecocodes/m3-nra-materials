package com.yourcompany.android.moviediary.ui.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.moviediary.model.MovieReview

@Composable
fun MovieItem(movieReview: MovieReview) {
  Card(
    Modifier
      .padding(12.dp)
      .fillMaxWidth()
  ) {
    Column(Modifier.padding(20.dp)) {
      Text(text = movieReview.title, fontSize = 20.sp, fontWeight = FontWeight.Medium)
      Text(text = movieReview.description)
      Text(text = movieReview.comment)
    }
  }
}
