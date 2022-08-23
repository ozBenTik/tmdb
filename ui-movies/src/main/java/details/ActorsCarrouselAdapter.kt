package details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core_ui.databinding.ActorCardBinding
import com.example.model.person.PersonMovieCast
import com.example.model.util.TmdbImageUrlProvider

class ActorsCarrouselAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
) :
    ListAdapter<PersonMovieCast, ActorCarrouselViewHolder>(ActorDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorCarrouselViewHolder {
        val binding = ActorCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActorCarrouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorCarrouselViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let {
            holder.binding.title.text = it.name
            holder.binding.subtitle.text = it.character

            entry.profilePath?.let { profile ->
                Glide.with(holder.itemView)
                    .load(
                        tmdbImageUrlProvider.getBackdropUrl(
                            path = profile,
                            imageWidth = holder.itemView.width
                        )
                    )
                    .into(holder.binding.imageView)
            }
        }
    }
}

class ActorCarrouselViewHolder(
    internal val binding: ActorCardBinding
) : RecyclerView.ViewHolder(binding.root)

object ActorDiff : DiffUtil.ItemCallback<PersonMovieCast>() {
    override fun areItemsTheSame(
        oldItem: PersonMovieCast,
        newItem: PersonMovieCast
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PersonMovieCast,
        newItem: PersonMovieCast
    ): Boolean {
        return oldItem == newItem
    }
}