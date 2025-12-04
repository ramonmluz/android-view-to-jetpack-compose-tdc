package br.com.ramonmluz.moviehub.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.presentation.ui.theme.MovieHubTheme
import br.com.ramonmluz.moviehub.presentation.ui.view.content.MovieDetailContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(action: () -> Unit = {} , movie: Movie) {
    MovieHubTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { action.invoke() }) {
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
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                MovieDetailContent(movie)
            }
        }
    }
}

