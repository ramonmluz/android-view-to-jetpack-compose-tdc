package br.com.ramonmluz.moviehub.presentation.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.presentation.ui.state.MovieState
import br.com.ramonmluz.moviehub.presentation.ui.theme.MovieHubTheme
import br.com.ramonmluz.moviehub.presentation.ui.view.content.MovieHomeContent
import br.com.ramonmluz.moviehub.presentation.ui.viewmodel.MovieViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieHomeScreen(viewModel: MovieViewModel = koinViewModel()) {
    MovieHubTheme {
        Scaffold(
            topBar = { TopBar() },
        ) { innerPadding ->

            var state by remember {
                mutableStateOf(MovieState())
            }

            LaunchedEffect(null) {
                viewModel.movieState.collect {
                    state = it
                }
            }

            MovieHomeContent(innerPadding, state, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}