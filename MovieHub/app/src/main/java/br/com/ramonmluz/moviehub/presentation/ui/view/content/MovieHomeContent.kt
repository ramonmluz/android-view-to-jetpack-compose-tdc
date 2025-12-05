package br.com.ramonmluz.moviehub.presentation.ui.view.content

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.presentation.ui.MovieDetailActivity
import br.com.ramonmluz.moviehub.presentation.ui.MovieDetailActivity.Companion.EXTRA_MOVIE
import br.com.ramonmluz.moviehub.presentation.ui.viewmodel.MovieViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieHomeContent(innerPadding: PaddingValues, viewModel: MovieViewModel = koinViewModel()) {
    val state = viewModel.movieState.collectAsState().value
    when {
        state.isLoading -> {
            ProgressIndicator()
        }

        state.movieResponse != null -> {
            MovieGridInitialization(
                movies = state.movieResponse.results,
                innerPadding = innerPadding
            )
        }

        else -> {
            AreaErrorInitialization(innerPadding, viewModel)
        }
    }
}

@Composable
private fun ProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun MovieGridInitialization(
    movies: List<Movie>, modifier: Modifier = Modifier, innerPadding: PaddingValues
) {
    LazyVerticalGrid(
        modifier = modifier.padding(innerPadding),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(movies) { movie ->
            MovieItem(movie)
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie
) {
    val context: Context = LocalContext.current
    val imageUrl: String = stringResource(R.string.base_url_image) + movie.posterPath

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(imageUrl).crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.local_movies),
            error = painterResource(R.drawable.ic_launcher),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { navigateToDetail(movie, context) })
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun navigateToDetail(movie: Movie, context: Context) {
    val intent = Intent(context, MovieDetailActivity::class.java)
    val jsonString = Json.encodeToString(movie)
    intent.putExtra(EXTRA_MOVIE, jsonString)
    context.startActivity(intent)
}

@Composable
fun AreaErrorInitialization(innerPadding: PaddingValues, viewModel: MovieViewModel) {
    Row(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .height(100.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = {
                viewModel.loadPopularMovies()
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_reload),
            contentDescription = null,
            modifier = Modifier
                .height(dimensionResource(R.dimen.load_img_size))
                .width(dimensionResource(R.dimen.load_img_size))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.white),
            fontSize = 16.sp,
            text = stringResource(R.string.error_listing_movies)
        )
    }
}

