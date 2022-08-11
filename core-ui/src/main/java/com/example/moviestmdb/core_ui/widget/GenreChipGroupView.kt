package com.example.moviestmdb.core_ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.core_ui.R
import com.example.core_ui.databinding.GenresChipGroupBinding
import com.google.android.material.chip.Chip

class GenreChipGroupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding =
        GenresChipGroupBinding.inflate(LayoutInflater.from(context), this, true)

    val chipsContainer = binding.genresGroup

    fun addGenre(name: String, id: Int, onGenreSelected: (()->Unit)?) {
        chipsContainer.addView(
            Chip(context).apply {
                this.id = id
                text = name
                isClickable = false
                isFocusable = false
                setTextColor(context.getColorStateList(R.color.chip_state_text))
                onGenreSelected?.let {
                    isClickable = true
                    isFocusable = true
                    isCheckable = true
                    chipBackgroundColor = context.getColorStateList(R.color.chip_state_background)
                    setOnClickListener { onGenreSelected() }
                }
            }
        )
    }
}