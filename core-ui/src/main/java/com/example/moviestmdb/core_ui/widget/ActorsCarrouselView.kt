package com.example.moviestmdb.core_ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.core_ui.databinding.ActorsCarrouselViewBinding

class ActorsCarrouselView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding =
        ActorsCarrouselViewBinding.inflate(LayoutInflater.from(context), this, true)

    val recyclerView: RecyclerView = binding.recyclerView
    val title: TextView = binding.title

    init {
        setLoading(false)
    }

    fun setLoading(loading: Boolean) {
        if (loading) {
            binding.loading.visibility = VISIBLE
        } else {
            binding.loading.visibility = INVISIBLE
        }
    }

}