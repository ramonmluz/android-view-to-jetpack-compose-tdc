package br.com.ramonmluz.moviehub.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.databinding.ActivityMovieDetailBinding
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Locale

@ExperimentalMaterial3Api
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movie: Movie
    private var originalMovieTitle: String = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonString = intent.getStringExtra(EXTRA_MOVIE)
        movie = jsonString?.let { Json.decodeFromString<Movie>(it) }!!
        originalMovieTitle = movie.originalTitle

        setupToolbar()
    }

    private fun setupToolbar() {
        binding.movieDetailAppBarComposeView.setContent {
            MovieDetailAppBar()
        }
    }

    @SuppressLint("ContextCastToActivity")
    @Composable
    private fun MovieDetailAppBar() {
        val headerImageHeight = 256.dp
        val imageUrl: String = getString(R.string.base_url_image) + movie.backdropPath
        val activity = LocalContext.current as Activity

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { activity.finish() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = colorResource(R.color.black),
                        navigationIconContentColor = colorResource(R.color.white),
                    ),
                    modifier = Modifier.height(56.dp)
                )
            },
            modifier = Modifier.background(colorResource(R.color.white))
        ) { innerPadding ->
            setupContent(headerImageHeight, innerPadding, imageUrl)
        }
    }

    private fun setupContent(
        headerImageHeight: Dp,
        innerPadding: PaddingValues,
        imageUrl: String
    ) {
        binding.movieDetailContentComposeView.setContent {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                MovieDeTailContent(headerImageHeight, imageUrl)
            }
        }
    }

    private fun getReleaseYear(releaseDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        return simpleDateFormat.format(simpleDateFormat.parse(releaseDate))
    }

    @Composable
    fun MovieDeTailContent(
        headerImageHeight: Dp,
        imageUrl: String
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(this@MovieDetailActivity)
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
                modifier = Modifier.padding( start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = getReleaseYear(movie.releaseDate),
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding( start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.overview,
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding( start = 16.dp,end = 16.dp)
            )
        }
    }

    companion object {
        // You can define the key here as well, or reference it from the Adapter
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
