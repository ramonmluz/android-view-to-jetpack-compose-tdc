package br.com.ramonmluz.moviehub.presentation.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.databinding.ActivityMainBinding
import br.com.ramonmluz.moviehub.presentation.ui.adapter.MovieAdapter
import br.com.ramonmluz.moviehub.presentation.ui.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutManager: LinearLayoutManager

    private val viewModel by viewModel<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEdgeToEdge()
        setupAppActionBar()
        setupObserver()
        setupRecyclerView()
        setupListener()
    }

    private fun setupEdgeToEdge() = with(binding) {
        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        enableEdgeToEdge()
    }

    private fun setupAppActionBar() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieState.collect { state ->

                    if (state.isLoading) {
                        showView(GONE, VISIBLE, GONE)
                    }

                    state.movieResponse?.let { movieResponse ->
                        with(binding) {
                            showView(VISIBLE, GONE, GONE)
                            recyclerView.adapter =
                                MovieAdapter(movieResponse.results, this@MainActivity)
                        }
                    }

                    state.error?.let {
                        showView(GONE, GONE, VISIBLE)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        layoutManager = GridLayoutManager(this, 2)

        with(binding) {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = MovieAdapter(emptyList(), this@MainActivity)
            addDecoration()
        }
    }

    private fun addDecoration() {
        with(binding) {
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val moviePosition: Int = parent.getChildLayoutPosition(view)

                    if (moviePosition == 0 || moviePosition == 1) {
                        outRect.top = getAnIntDp(16)
                        defineMarginBottom(outRect)
                    } else {
                        defineMarginBottom(outRect)
                    }

                    if (moviePosition % 2 == 0) {
                        defineMargin(outRect, 16, 8)
                    } else {
                        defineMargin(outRect, 8, 16)
                    }
                }
            })
        }
    }

    fun defineMarginBottom(rect: Rect?) {
        rect?.bottom = getAnIntDp(16)
    }

    fun defineMargin(rect: Rect?, marginLeft: Int, marginRight: Int) {
        rect?.left = getAnIntDp(marginLeft)
        rect?.right = getAnIntDp(marginRight)
    }

    fun getAnIntDp(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    fun onLoad() {
        showView(GONE, VISIBLE, GONE)
    }

    private fun showView(
        recyclerViewVisibility: Int, progressVisibility: Int, errorVisibility: Int
    ) {
        with(binding) {
            recyclerView.visibility = recyclerViewVisibility
            progress.visibility = progressVisibility
            errorArea.visibility = errorVisibility
        }
    }

    fun setupListener() = with(binding) {
        errorArea.setOnClickListener {
            onLoad()
            viewModel.loadPopularMovies()
        }
    }
}