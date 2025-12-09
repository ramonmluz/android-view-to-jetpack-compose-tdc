package br.com.ramonmluz.moviehub.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.presentation.ui.view.MovieDetailScreen
import kotlinx.serialization.json.Json

@ExperimentalMaterial3Api
class MovieDetailActivity : ComponentActivity() {

    private lateinit var movie: Movie
    private var originalMovieTitle: String = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonString = intent.getStringExtra(EXTRA_MOVIE)
        movie = jsonString?.let { Json.decodeFromString<Movie>(it) }!!
        originalMovieTitle = movie.originalTitle

        setContent {
            MovieDetailScreen(action = ::onFinishActivity, movie)
        }
    }

    fun onFinishActivity() {
        finish()
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
