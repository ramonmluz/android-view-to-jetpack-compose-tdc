package br.com.ramonmluz.moviehub.presentation.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.databinding.ActivityMovieDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs

class MovieDetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movie: Movie
    private var isToolbarCollapsed = false
    private var upArrowDrawable: Drawable? = null
    private var collapsedIconColor: Int = Color.WHITE // Default, will be updated
    private var expandedIconColor: Int = Color.WHITE
    private var originalMovieTitle: String = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonString = intent.getStringExtra(EXTRA_MOVIE)
        movie = jsonString?.let { Json.decodeFromString<Movie>(it) }!!
        originalMovieTitle = movie.originalTitle

        loadImage()
        setupToolbar()
        setupContent()

    }

    private fun setupToolbar() {
        with(binding) {
            setSupportActionBar(toolbarMovieDetail)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            collapsingToolbar.title = " "
            collapsingToolbar.setCollapsedTitleTextColor(collapsedIconColor)

            upArrowDrawable = ContextCompat.getDrawable(
                this@MovieDetailActivity,
                androidx.appcompat.R.drawable.abc_ic_ab_back_material
            )?.mutate()
            updateUpArrowColor(expandedIconColor)

            // Add the offset listener to the AppBarLayout
            appBarMovieDetail.addOnOffsetChangedListener(this@MovieDetailActivity)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val totalScrollRange = appBarLayout?.totalScrollRange ?: 0
        val currentScrollPercentage = abs(verticalOffset).toFloat() / totalScrollRange.toFloat()

        if (currentScrollPercentage >= 0.9f) { // Consider it collapsed if 90% or more is scrolled
            if (!isToolbarCollapsed) {
                updateUpArrowColor(collapsedIconColor)
                binding.collapsingToolbar.title = originalMovieTitle
                isToolbarCollapsed = true
            }
        } else {
            if (isToolbarCollapsed) {
                updateUpArrowColor(expandedIconColor)
                binding.collapsingToolbar.title = String()
                isToolbarCollapsed = false
            }
        }
    }

    private fun updateUpArrowColor(color: Int) {
        upArrowDrawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrowDrawable)
    }

    private fun setupContent() {
        binding.movieDetailContentComposeView.setContent {
            MovieDeTailContent()
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

    @Composable
    fun MovieDeTailContent() {
        Column(
            modifier  = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = getReleaseYear(movie.releaseDate),
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = movie.overview,
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }

    companion object {
        // You can define the key here as well, or reference it from the Adapter
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
