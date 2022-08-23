package toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.movie.Movie
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_movies.databinding.ListItemTopRatedMovieBinding

class TopRatedMoviesAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
//    private val tmdbDateFormatter: TmdbDateFormatter,
    private val onItemClickListener: (movieId: Int) -> Unit,
) : PagingDataAdapter<Movie, TopRatedMovieViewHolder>(TopRatedEntryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMovieViewHolder {
        val binding = ListItemTopRatedMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopRatedMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopRatedMovieViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let { popularEntry ->
            holder.binding.title.text = popularEntry.title
            holder.binding.subtitle.text = "${popularEntry.voteCount} votes \u00B7 ${popularEntry.releaseDate}"

            holder.binding.popularityBadge.progress = popularEntry.popularityPrecentage

            entry.posterPath?.let { posterPath ->
                Glide.with(holder.itemView)
                    .load(
                        tmdbImageUrlProvider.getPosterUrl(
                            path = posterPath,
                            imageWidth = holder.itemView.width,
                        )
                    )
                    .into(holder.binding.image)
            }



            holder.binding.root.setOnClickListener {
                onItemClickListener(entry.id)
            }
        }

    }
}

class TopRatedMovieViewHolder(
    internal val binding: ListItemTopRatedMovieBinding
) : RecyclerView.ViewHolder(binding.root)

object TopRatedEntryComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        return oldItem == newItem
    }
}