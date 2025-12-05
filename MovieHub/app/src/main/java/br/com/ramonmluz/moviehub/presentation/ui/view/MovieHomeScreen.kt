package br.com.ramonmluz.moviehub.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.presentation.ui.theme.MovieHubTheme
import br.com.ramonmluz.moviehub.presentation.ui.view.content.MovieHomeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieHomeScreen() {
    MovieHubTheme {
        Scaffold(
            topBar = { TopBar() },
            modifier = Modifier.background(colorResource(R.color.white))
        ) { innerPadding ->
            MovieHomeContent(innerPadding)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            titleContentColor = colorResource(R.color.white),
            containerColor = colorResource(R.color.black),
            navigationIconContentColor = colorResource(R.color.white),
        ),
        modifier = Modifier.height(56.dp)
    )
}