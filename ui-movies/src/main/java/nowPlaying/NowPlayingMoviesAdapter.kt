package nowPlaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.Movie
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_movies.databinding.ListItemNowPlayingMovieBinding

class NowPlayingMoviesAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
//    private val tmdbDateFormatter: TmdbDateFormatter,
    private val onItemClickListener: (movieId: Int) -> Unit,
) : PagingDataAdapter<Movie, NowPlayingMovieViewHolder>(NowPlayingEntryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMovieViewHolder {
        val binding = ListItemNowPlayingMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NowPlayingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let { popularEntry ->
            holder.binding.title.text = popularEntry.title
            holder.binding.subtitle.text = "${popularEntry.voteCount} votes \u00B7 ${popularEntry.releaseDate}"

//            holder.binding.subtitle.text = "${popularEntry.voteCount} votes • ${
//                tmdbDateFormatter.formatMediumDate(popularEntry.releaseDate)
//            }"

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

class NowPlayingMovieViewHolder(
    internal val binding: ListItemNowPlayingMovieBinding
) : RecyclerView.ViewHolder(binding.root)

object NowPlayingEntryComparator : DiffUtil.ItemCallback<Movie>() {
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