package com.example.ui_userdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core_ui.databinding.MovieCardBinding
import com.example.model.Movie
import com.example.model.util.TmdbImageUrlProvider
import com.example.ui_userdetails.databinding.FavoriteItemBinding

class FavoriteMovieCarouselAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
    private val onItemClickListener: (movieId: Int) -> Unit,
) :
    ListAdapter<Movie, FavoriteMovieCarrouselViewHolder>(FavoriteMovieDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMovieCarrouselViewHolder {
        val binding = FavoriteItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteMovieCarrouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieCarrouselViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let {

            entry.posterPath?.let { posterPath ->
                Glide.with(holder.itemView)
                    .load(
                        tmdbImageUrlProvider.getPosterUrl(
                            path = posterPath,
                            imageWidth = holder.itemView.width
                        )
                    )
                    .into(holder.binding.movieImage)
            }

            holder.itemView.setOnClickListener {
                onItemClickListener(entry.id)
            }
        }
    }
}

class FavoriteMovieCarrouselViewHolder(
    internal val binding: FavoriteItemBinding
) : RecyclerView.ViewHolder(binding.root)

object FavoriteMovieDiff : DiffUtil.ItemCallback<Movie>() {
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