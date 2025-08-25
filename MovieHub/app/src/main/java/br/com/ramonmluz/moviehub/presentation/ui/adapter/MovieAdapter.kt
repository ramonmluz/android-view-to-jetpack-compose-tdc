package br.com.ramonmluz.moviehub.presentation.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ramonmluz.moviehub.R
import br.com.ramonmluz.moviehub.data.model.Movie
import br.com.ramonmluz.moviehub.databinding.ViewMovieItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MovieAdapter(val items: List<Movie>, val context: Context) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewMovieItemBinding =
            ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_movie_item, parent, false)
        return ViewHolder(viewMovieItemBinding)
    }

    override fun getItemCount(): Int {
        return items.size ?: 0
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
            .listener(object : RequestListener<Drawable> { // <-- ADD LISTENER
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    Log.e("MovieAdapter", "Glide onLoadFailed for URL: $imageUrl", e) // <-- LOG THE EXCEPTION
//                    // e might be null, but GlideException usually has good details.
//                    // You can inspect 'e' in the debugger if it's not null.
//                    return false // MUST return false to allow Glide to set the error drawable
//                }

                //                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any,
//                    target: Target<Drawable>,
//                    dataSource: DataSource,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    Log.i("MovieAdapter", "Glide onResourceReady for URL: $imageUrl")
//                    return false // MUST return false to allow Glide to set the loaded image
//                }
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("MovieAdapter", "Glide onResourceReady for URL: $imageUrl")
                    return false // MUST return false to allow Glide to set the loaded image
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i("MovieAdapter", "Glide onResourceReady for URL: $imageUrl")
                    return false // MUST return false to allow Glide to set the l
                }
            })
            .into(holder.imageView)
    }

    class ViewHolder(binding: ViewMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.movieImageGrid
    }

}
