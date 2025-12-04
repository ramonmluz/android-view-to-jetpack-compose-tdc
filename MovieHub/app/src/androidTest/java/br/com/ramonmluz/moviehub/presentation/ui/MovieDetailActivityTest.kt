package br.com.ramonmluz.moviehub.presentation.ui

import android.content.Intent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import br.com.ramonmluz.moviehub.stubs.MovieStub
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class MovieDetailActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MovieDetailActivity>()

    private val movieStub = MovieStub.getMovieStub()

    init {
        // Create an intent with the movie data
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            MovieDetailActivity::class.java
        ).apply {
            putExtra(MovieDetailActivity.EXTRA_MOVIE, Json.encodeToString(movieStub))
        }
    }

    @Test
    fun shouldDisplayedMovieDetailsCorrectly_whenActivityIsLaunched() {
        composeTestRule.onNodeWithText(movieStub.originalTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(getReleaseYear(movieStub.releaseDate)).assertIsDisplayed()
        composeTestRule.onNodeWithText(movieStub.overview).assertIsDisplayed()
    }
}

private fun getReleaseYear(releaseDate: String): String {
    val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    return simpleDateFormat.format(simpleDateFormat.parse(releaseDate)!!)
}