package br.com.ramonmluz.moviehub.presentation.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import br.com.ramonmluz.moviehub.databinding.ActivityMainBinding
import br.com.ramonmluz.moviehub.presentation.ui.view.MovieHomeScreen

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setupEdgeToEdge()
//        setupObserver()

        setContent {
            MovieHomeScreen()
        }
    }

//    private fun setupEdgeToEdge() = with(binding) {
//        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        enableEdgeToEdge()
//    }

//    private fun setupObserver() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.movieState.collect { state ->
//
//                    if (state.isLoading) {
//                        with(binding) {
//                            showView(progressVisibility = VISIBLE)
//                            progress.setContent {
//                                ProgressIndicator()
//                            }
//                        }
//                    }
//
//                    state.movieResponse?.let { movieResponse ->
//                        with(binding) {
//                            showView(recyclerViewVisibility = VISIBLE)
//                            composeRecyclerView.setContent {
//                                TopBar(state)
//                            }
//                        }
//                    }
//
//                    state.error?.let {
//                        with(binding) {
//                            composeRecyclerView.setContent {
//                                TopBar(state)
//                                showView(recyclerViewVisibility = VISIBLE)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

//    @Composable
//    private fun ProgressIndicator() {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator(
//                modifier = Modifier.size(48.dp),
//                color = MaterialTheme.colorScheme.primary,
//            )
//        }
//    }



//    fun onLoad() {
//        showView(progressVisibility = VISIBLE)
//    }
//
//    private fun showView(
//        recyclerViewVisibility: Int = GONE,
//        progressVisibility: Int = GONE,
//        errorVisibility: Int = GONE
//    ) {
//        with(binding) {
//            composeRecyclerView.visibility = recyclerViewVisibility
//            progress.visibility = progressVisibility
////            errorArea.visibility = errorVisibility
//        }
//    }


}