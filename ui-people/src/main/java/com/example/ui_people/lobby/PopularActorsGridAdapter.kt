package com.example.ui_people.lobby
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.PopularActor
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_people.databinding.PopularActorCardBinding

class PopularActorsAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
    private val onItemClickListener: (movieId: Int) -> Unit,
) : PagingDataAdapter<PopularActor, PopularActorViewHolder>(PopularActorEntryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularActorViewHolder {
        val binding = PopularActorCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PopularActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularActorViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let { popularEntry ->
            holder.binding.title.text = popularEntry.name
            holder.binding.subtitle.text = popularEntry.knownForTitles

            entry.profilePath?.let { posterPath ->
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

class PopularActorViewHolder(
    internal val binding: PopularActorCardBinding
) : RecyclerView.ViewHolder(binding.root)

object PopularActorEntryComparator : DiffUtil.ItemCallback<PopularActor>() {
    override fun areItemsTheSame(
        oldItem: PopularActor,
        newItem: PopularActor
    ): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PopularActor,
        newItem: PopularActor
    ): Boolean {
        return oldItem == newItem
    }
}