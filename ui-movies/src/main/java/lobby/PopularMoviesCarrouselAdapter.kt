package lobby

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core_ui.databinding.MovieCardBinding
import com.example.model.Movie
import com.example.model.util.TmdbImageUrlProvider

class PopularMoviesCarrouselAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
    private val onItemClickListener: (movieId: Int) -> Unit,
) :
    ListAdapter<Movie, PopularMovieCarrouselViewHolder>(PopularMovieDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMovieCarrouselViewHolder {
        val binding = MovieCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PopularMovieCarrouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMovieCarrouselViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let {
            holder.binding.title.text = it.title
            holder.binding.subtitle.text = it.releaseDate

            entry.posterPath?.let { posterPath ->
                Glide.with(holder.itemView)
                    .load(
                        tmdbImageUrlProvider.getPosterUrl(
                            path = posterPath,
                            imageWidth = holder.itemView.width
                        )
                    )
                    .into(holder.binding.imageView)
            }

            holder.binding.popularityBadge.progress = entry.popularityPrecentage

            holder.itemView.setOnClickListener {
                onItemClickListener(entry.id)
            }
        }


    }
}

class PopularMovieCarrouselViewHolder(
    internal val binding: MovieCardBinding
) : RecyclerView.ViewHolder(binding.root)

object PopularMovieDiff : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        return oldItem == newItem
    }
}