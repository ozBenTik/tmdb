package details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core_ui.databinding.MovieCardBinding
import com.example.core_ui.databinding.RecommendedCardBinding
import com.example.model.Movie
import com.example.model.util.TmdbImageUrlProvider

class RecommendedMoviesCarrouselAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
) :
    ListAdapter<Movie, RecommendedMovieCarrouselViewHolder>(RecommendedMovieDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendedMovieCarrouselViewHolder {
        val binding = RecommendedCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RecommendedMovieCarrouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedMovieCarrouselViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let {
            holder.binding.title.text = it.title

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

        }
    }
}

class RecommendedMovieCarrouselViewHolder(
    internal val binding: RecommendedCardBinding
) : RecyclerView.ViewHolder(binding.root)

object RecommendedMovieDiff : DiffUtil.ItemCallback<Movie>() {
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