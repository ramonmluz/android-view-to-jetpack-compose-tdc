package br.com.ramonmluz.moviehub.presentation.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.databinding.ActivityMovieDetailBinding
import com.bumptech.glide.Glide
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Locale

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movie: Movie
    private var upArrowDrawable: Drawable? = null
    private var expandedIconColor: Int = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonString = intent.getStringExtra(EXTRA_MOVIE)
        movie = jsonString?.let { Json.decodeFromString<Movie>(it) }!!

        loadImage()
        setupToolbar()
        setupContent()
    }

    private fun setupToolbar() {
        with(binding) {
            setSupportActionBar(toolbarMovieDetail)
            supportActionBar?.title = String()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            upArrowDrawable = ContextCompat.getDrawable(
                this@MovieDetailActivity,
                androidx.appcompat.R.drawable.abc_ic_ab_back_material
            )?.mutate()
            updateUpArrowColor(expandedIconColor)
        }
    }

    private fun updateUpArrowColor(color: Int) {
        upArrowDrawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrowDrawable)
    }

    private fun setupContent() {
        with(binding) {
            movieTitle.text = movie.originalTitle
            releaseDateDatail.text = getReleaseYear(movie.releaseDate)
            overviewDetail.text = movie.overview
        }
    }

    private fun getReleaseYear(releaseDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        return simpleDateFormat.format(simpleDateFormat.parse(releaseDate))
    }

    fun loadImage() {
        val imageUrl: String = getString(R.string.base_url_image) + movie.backdropPath
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.mipmap.local_movies)
            .error(R.mipmap.ic_launcher)
            .into(binding.movieImageDetail)
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
