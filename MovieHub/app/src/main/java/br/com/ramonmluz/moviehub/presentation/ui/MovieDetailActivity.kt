package br.com.ramonmluz.moviehub.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.databinding.ActivityMovieDetailBinding
import com.bumptech.glide.Glide
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonString = intent.getStringExtra(EXTRA_MOVIE)
        movie = jsonString?.let { Json.decodeFromString<Movie>(it) }!!

        loadImage()
        with(binding) {
//            toolbarMovieDetail.setSubtitleTextColor(getColor(R.color.white))
//            toolbarMovieDetail.setTitleTextColor(getColor(R.color.white))
            setSupportActionBar(toolbarMovieDetail)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            collapsingToolbar.title = movie.originalTitle
            collapsingToolbar.setCollapsedTitleTextColor(getColor(R.color.white))
            releaseDateDatail.text = getReleaseYear(movie.releaseDate)
            overviewDetail.text = movie.overview
        }
    }

    private fun getReleaseYear(realeaseDate: String): String {
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy")
        return simpleDateFormat.format(simpleDateFormat.parse(realeaseDate))
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
        // You can define the key here as well, or reference it from the Adapter
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
