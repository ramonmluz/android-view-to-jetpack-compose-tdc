package br.com.ramonmluz.moviehub.presentation.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.databinding.ActivityMainBinding
import br.com.ramonmluz.moviehub.presentation.ui.adapter.MovieAdapter
import br.com.ramonmluz.moviehub.presentation.ui.state.MovieState
import br.com.ramonmluz.moviehub.presentation.ui.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutManager: LinearLayoutManager
    private var movies = mutableListOf<Movie>()
    private var scrollOutItems: Int = 0
    private var isScrolling: Boolean = false
    private var isNextPage: Boolean = false
    private var isGenericError: Boolean = false
    private var isLastPage: Boolean = false

    private val viewModel by viewModel<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        setContentView(binding.root)
        initRecyclerView()
        setupFlowCollectors()

    }

    private fun setupFlowCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieState.collect { state ->
                    when(state){
                        is MovieState.Initial -> {
                            showView(GONE, VISIBLE, GONE, GONE)
                        }
                        is MovieState.Loading -> {
                            showView(GONE, VISIBLE, GONE, VISIBLE)
                        }
                        is MovieState.Error -> {
                            showView(GONE, GONE, VISIBLE, GONE)
                        }

                        is MovieState.Success -> {
                            showView(VISIBLE, GONE, GONE, GONE)
                            movies.addAll(state.data.results)
                            binding.recyclerView.adapter = MovieAdapter(movies, this@MainActivity)
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        layoutManager = GridLayoutManager(this, 2)

        with(binding) {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = MovieAdapter(emptyList(), this@MainActivity)
            addInfitePagination()
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val moviePosition: Int = parent.getChildLayoutPosition(view)

                    if (moviePosition == 0 || moviePosition == 1) {
                        outRect.top = getAnIntDp(8)
                        defineMarginBottom(outRect)
                    } else {
                        defineMarginBottom(outRect)
                    }

                    if (moviePosition % 2 == 0) {
                        defineMargin(outRect, 8, 4)
                    } else {
                        defineMargin(outRect, 4, 8)
                    }
                }
            })
        }
    }

    fun addInfitePagination() {
        with(binding) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    scrollOutItems = layoutManager.findFirstCompletelyVisibleItemPosition()
                    isNextPage = true

                    val currentItens: Int = layoutManager.childCount;
                    val totalItems: Int = layoutManager.getItemCount();
                    scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                    // Verifica se foi feito um scroll, se está no ultimo registro e
                    // se ultima página de repósitorios não foi obtida
                    if (isScrolling && (currentItens + scrollOutItems == totalItems) && !isLastPage) {
                        isScrolling = false;
                        // Obtem próxima página de reppsitorios
                        isNextPage = true
                        loadMovies()
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == newState) {
                        isScrolling = true
                    }
                }
            })
        }
    }

    fun defineMarginBottom(rect: Rect?) {
        rect?.bottom = getAnIntDp(8)
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

    fun loadMovies() {
        if (isNextPage) {
            showView(VISIBLE, GONE, GONE, VISIBLE)
        } else {
            showView(GONE, VISIBLE, GONE, GONE)
        }
    }

    fun showView(
        recyclerViewVisibility: Int, progressVisibility: Int,
        areaErroVisibility: Int, progressNextPageVisibility: Int
    ) {
        with(binding) {
            recyclerView.visibility = recyclerViewVisibility
            progress.root.visibility = progressVisibility
            areaErro.visibility = areaErroVisibility
            progressNextPage.visibility = progressNextPageVisibility
        }
    }

    fun reloadMovies(view: View) {
        loadMovies()
    }
}