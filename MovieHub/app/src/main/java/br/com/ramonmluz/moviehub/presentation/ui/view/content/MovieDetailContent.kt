package br.com.ramonmluz.moviehub.presentation.ui.view.content

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MovieDetailContent(movie: Movie) {

    val context: Context = LocalContext.current
    val headerImageHeight: Dp = 256.dp
    val imageUrl: String = context.getString(R.string.base_url_image) + movie.posterPath
    Log.d("image", imageUrl)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.local_movies),
            error = painterResource(R.drawable.ic_launcher),
            modifier = Modifier
                .fillMaxWidth()
                .height(headerImageHeight)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.originalTitle,
            color = colorResource(R.color.black),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = getReleaseYear(movie.releaseDate),
            color = colorResource(R.color.black),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.overview,
            color = colorResource(R.color.black),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}

private fun getReleaseYear(releaseDate: String): String {
    val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    return simpleDateFormat.format(simpleDateFormat.parse(releaseDate))
}
