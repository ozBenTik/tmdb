package nowPlaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.Genre
import com.example.model.Movie
import com.example.model.MovieAndGenres
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_movies.databinding.ItemNowplayingMovieBinding
import kotlinx.coroutines.flow.flowOf

class NowPlayingMoviesAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
    private val onItemClickListener: (movieId: Int) -> Unit,
) : PagingDataAdapter<MovieAndGenres, NowPlayingMovieViewHolder>(NowPlayingEntryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMovieViewHolder {
        val binding = ItemNowplayingMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NowPlayingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let { popularEntry ->

            holder.binding.title.text = popularEntry.movie.title
            holder.binding.subtitle.text = "${popularEntry.movie.voteCount} votes \u00B7 ${popularEntry.movie.releaseDate}"
            holder.binding.genresChipGroup.chipsContainer.removeAllViews()

            holder.binding.popularityBadge.progress = popularEntry.movie.popularityPrecentage
            popularEntry.genres.forEach { genre ->
                genre.name?.takeIf { genre.id != null }?.let {genreName ->
                    holder.binding.genresChipGroup.addGenre(genreName, genre.id!!, null)
                }
            }

            popularEntry.movie.posterPath?.let { posterPath ->
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
                onItemClickListener(entry.movie.id)
            }
        }

    }
}

class NowPlayingMovieViewHolder(
    internal val binding: ItemNowplayingMovieBinding
) : RecyclerView.ViewHolder(binding.root)

object NowPlayingEntryComparator : DiffUtil.ItemCallback<MovieAndGenres>() {
    override fun areItemsTheSame(
        oldItem: MovieAndGenres,
        newItem: MovieAndGenres
    ): Boolean {
        // Id is unique.
        return oldItem.movie.id == newItem.movie.id
    }

    override fun areContentsTheSame(
        oldItem: MovieAndGenres,
        newItem: MovieAndGenres
    ): Boolean {
        return oldItem == newItem
    }
}