package br.com.ramonmluz.moviehub.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.databinding.ViewMovieItemBinding
import com.bumptech.glide.Glide

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
        navigateToDatail(holder)
    }

    private fun navigateToDatail(holder: ViewHolder) {
        holder.imageView.setOnClickListener { view ->
            //            var context: Context = view.context
            //            var intent: Intent = Intent(view.context, MovieDatailActivity::class.java)
            //            intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, movie)
            //            context.startActivity(intent)
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
