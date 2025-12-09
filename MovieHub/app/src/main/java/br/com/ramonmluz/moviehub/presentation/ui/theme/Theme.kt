package br.com.ramonmluz.moviehub.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import br.com.ramonmluz.moviehub.R

@Composable
fun MovieHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val darkColors = darkColorScheme(
        primary = colorResource(R.color.black),
    )

    val lightColors = lightColorScheme(
        primary = colorResource(R.color.black),
    )

    val colors = if (darkTheme) darkColors else lightColors
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

