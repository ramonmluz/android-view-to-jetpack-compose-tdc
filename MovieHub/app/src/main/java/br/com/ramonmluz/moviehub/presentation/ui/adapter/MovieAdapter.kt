package br.com.ramonmluz.moviehub.presentation.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.databinding.ViewMovieItemBinding
import br.com.ramonmluz.moviehub.presentation.ui.MovieDetailActivity
import br.com.ramonmluz.moviehub.presentation.ui.MovieDetailActivity.Companion.EXTRA_MOVIE
import com.bumptech.glide.Glide
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.jvm.java

class MovieAdapter(val items: List<Movie>, val context: Context) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewMovieItemBinding =
            ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewMovieItemBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie = items[position]
        val imageUrl: String = context.getString(R.string.base_url_image) + movie.posterPath
        loadImage(imageUrl, holder)
        navigateToDetail(holder, movie)
    }

    private fun navigateToDetail(holder: ViewHolder, movie: Movie) {
        holder.imageView.setOnClickListener { view ->
            val intent = Intent(view.context, MovieDetailActivity::class.java)
            val jsonString = Json.encodeToString(movie)
            intent.putExtra(EXTRA_MOVIE, jsonString)
            view.context.startActivity(intent)
        }
    }

    private fun loadImage(
        imageUrl: String,
        holder: ViewHolder
    ) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.mipmap.local_movies)
            .error(R.mipmap.ic_launcher)
            .into(holder.imageView)
    }

    class ViewHolder(binding: ViewMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.movieImageGrid
    }

}
