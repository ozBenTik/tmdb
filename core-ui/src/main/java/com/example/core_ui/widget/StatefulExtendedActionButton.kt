package com.example.core_ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core_ui.databinding.ActionButtonWithStateBinding
import com.example.core_ui.databinding.PopularityBadgeViewBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class StatefulExtendedActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ExtendedFloatingActionButton(context, attrs, defStyleAttr) {

    private lateinit var binding : ActionButtonWithStateBinding

    private var offState = ActionState.Off()
    private var onState = ActionState.On()

    private var currentState: ActionState = ActionState.Off()
        set(value) {
            field = value
            binding.root.apply {
                icon = value.icon
                text = value.text
            }
        }

    init {
        binding = ActionButtonWithStateBinding.inflate(LayoutInflater.from(context), super.getParent() as? ViewGroup, true)
    }

    fun initStates(off: ActionState.Off, on: ActionState.On) {
        this.offState = off
        this.onState = on
    }

    fun toggleView(onToggled : ((updatedState: ActionState) -> Unit)? = null) {
        this.currentState = when(this.currentState) {
            is ActionState.On -> offState
            is ActionState.Off -> onState
        }.also {
            onToggled?.invoke(this.currentState)
        }
    }

    sealed class ActionState(open val icon: Drawable?, open val text: String) {
        data class On(override val icon: Drawable? = null, override val text: String = "") :
            ActionState(icon, text)

        data class Off(override val icon: Drawable? = null, override val text: String = "") :
            ActionState(icon, text)
    }
}