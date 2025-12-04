package br.com.ramonmluz.moviehub.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.databinding.ActivityMainBinding
import br.com.ramonmluz.moviehub.presentation.ui.MovieDetailActivity.Companion.EXTRA_MOVIE
import br.com.ramonmluz.moviehub.presentation.ui.state.MovieState
import br.com.ramonmluz.moviehub.presentation.ui.viewmodel.MovieViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setupEdgeToEdge()
//        setupAppActionBar()
        setupObserver()
    }

    private fun setupEdgeToEdge() = with(binding) {
//        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        enableEdgeToEdge()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieState.collect { state ->

                    if (state.isLoading) {
                        showView(progressVisibility = VISIBLE)
                    }

                    state.movieResponse?.let { movieResponse ->
                        with(binding) {
                            showView(recyclerViewVisibility = VISIBLE)
                            composeRecyclerView.setContent {
                                TopBar(state)
                            }
                        }
                    }

                    state.error?.let {
                        with(binding) {
                            composeRecyclerView.setContent {
                                TopBar(state)
                                showView(recyclerViewVisibility = VISIBLE)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TopBar(
        movieState: MovieState
    ) = with(binding) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(getString(R.string.app_name)) },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        titleContentColor = colorResource(R.color.white),
                        containerColor = colorResource(R.color.black),
                        navigationIconContentColor = colorResource(R.color.white),
                    ),
                    modifier = Modifier.height(56.dp)
                )
            }, modifier = Modifier.background(colorResource(R.color.white))
        ) { innerPadding ->

            when {
                movieState.movieResponse != null -> {
                    MovieGridInitialization(
                        movies = movieState.movieResponse.results,
                        innerPadding = innerPadding
                    )
                }

                movieState.error != null -> {
                    AreaErrorInitialization(innerPadding = innerPadding)
                }
                // movieState.isLoading -> { ... }
            }
        }
    }

    @Composable
    fun AreaErrorInitialization(innerPadding: PaddingValues) {
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clickable(onClick = ::onClickAreaError),
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

    fun onClickAreaError() {
        onLoad()
        viewModel.loadPopularMovies()
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
        val imageUrl: String = getString(R.string.base_url_image) + movie.posterPath

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(this@MainActivity).data(imageUrl).crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.local_movies),
                error = painterResource(R.drawable.ic_launcher),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { navigateToDetail(movie) })
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun navigateToDetail(movie: Movie) {
        val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
        val jsonString = Json.encodeToString(movie)
        intent.putExtra(EXTRA_MOVIE, jsonString)
        startActivity(intent)
    }

    fun onLoad() {
        showView(progressVisibility = VISIBLE)
    }

    private fun showView(
        recyclerViewVisibility: Int = GONE,
        progressVisibility: Int = GONE,
        errorVisibility: Int = GONE
    ) {
        with(binding) {
            composeRecyclerView.visibility = recyclerViewVisibility
            progress.visibility = progressVisibility
//            errorArea.visibility = errorVisibility
        }
    }


}