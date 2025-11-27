package br.com.ramonmluz.moviehub.presentation.ui

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.stubs.MovieStub
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest {

    private val activityIntent: Intent =
        Intent(ApplicationProvider.getApplicationContext(), MovieDetailActivity::class.java).apply {
            val movieJson = Json.encodeToString(MovieStub.getMovieStub())
            putExtra(MovieDetailActivity.EXTRA_MOVIE, movieJson)
        }

    @get:Rule
    val activityRule = ActivityScenarioRule<MovieDetailActivity>(activityIntent)

    @Test
    fun shouldDisplayedMovieDetailsCorrectly_whenActivityIsLaunched() {
        val movieStub = MovieStub.getMovieStub()

        // 1. Verify the movie title is displayed.
        onView(withId(R.id.titleTxt))
            .check(matches(isDisplayed()))
            .check(matches(withText(movieStub.originalTitle)))

        // 2. Verify the release year is displayed correctly.
        val expectedYear = getReleaseYear(movieStub.releaseDate)
        onView(withId(R.id.releaseDateDatail))
            .check(matches(isDisplayed()))
            .check(matches(withText(expectedYear)))

        // 3. Verify the overview text is displayed.
        onView(withId(R.id.overviewDetail))
            .check(matches(isDisplayed()))
            .check(matches(withText(movieStub.overview)))
    }

    private fun getReleaseYear(releaseDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        return simpleDateFormat.format(simpleDateFormat.parse(releaseDate)!!)
    }
}
