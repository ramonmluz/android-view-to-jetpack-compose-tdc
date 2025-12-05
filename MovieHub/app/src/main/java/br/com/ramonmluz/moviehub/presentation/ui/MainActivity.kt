package br.com.ramonmluz.moviehub.presentation.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import br.com.ramonmluz.moviehub.presentation.ui.view.MovieHomeScreen

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setupEdgeToEdge()

        setContent {
            MovieHomeScreen()
        }
    }
}