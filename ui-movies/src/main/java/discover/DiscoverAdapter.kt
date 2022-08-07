package discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.Movie
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_movies.databinding.DiscoveryCardBinding

class DiscoverAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
    private val onItemClickListener: (movieId: Int) -> Unit,
) : PagingDataAdapter<Movie, DiscoveryViewHolder>(TopRatedEntryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoveryViewHolder {
        val binding = DiscoveryCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DiscoveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscoveryViewHolder, position: Int) {
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
                    .into(holder.binding.imageView)
            }



            holder.binding.root.setOnClickListener {
                onItemClickListener(entry.id)
            }
        }

    }
}

class DiscoveryViewHolder(
    internal val binding: DiscoveryCardBinding
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