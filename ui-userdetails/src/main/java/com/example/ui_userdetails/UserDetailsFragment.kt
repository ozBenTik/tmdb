package com.example.ui_userdetails

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.TmdbImageManager
import com.example.moviestmdb.core_ui.util.SpaceItemDecoration
import com.example.ui_userdetails.databinding.FragmentUserDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import java.net.MalformedURLException
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    lateinit var binding: FragmentUserDetailsBinding
    val viewModel: UserViewModel by viewModels()

    lateinit var favoriteMoviesAdapter: FavoriteMovieCarouselAdapter

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteMoviesView.more.visibility = View.GONE
        binding.favoriteMoviesView.title.visibility = View.GONE

        initFavoritesAdapter()

        launchAndRepeatWithViewLifecycle {
            viewModel.state.collect { uiState ->

                if (uiState != UserDetailsViewState.Empty) {

                    uiState.message?.let { message ->
                        Snackbar.make(requireView(), message.message, Snackbar.LENGTH_LONG)
                            .setAction("Dismiss") {
                                viewModel.clearMessage(message.id)
                            }
                            .show()
                    }

                    Glide.with(view)
                        .load(
                            uiState.userDetails?.getPhotoUrl()
                                ?: "https://media.istockphoto.com/vectors/sunglasses-emoticon-with-big-smile-vector-id1191260149"
                        ).into(binding.userImage)

                    uiState.userDetails?.getEmail()?.let {
                        binding.userEmailTextView.text = it
                    }

                    uiState.userDetails?.getDisplayName()?.takeIf { it.isNotEmpty() }?.let {
                        binding.userNameTextView.text = it
                    }

                    binding.favoriteMoviesView.setLoading(uiState.favoritesRefreshing)

                    if (uiState.favorites.isEmpty()) {
                        binding.titleFavoritesTextView.text = "No Favorite Movies"
                    } else {
                        binding.titleFavoritesTextView.text = "Favorite Movies"
                    }

                    favoriteMoviesAdapter.submitList(uiState.favorites)


                    binding.userLoadingView.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE

                }
            }
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                com.example.core_ui.R.id.signout_action_item -> {
                    viewModel.logout()
                    true
                }
                else -> false
            }
        }

        binding.userNameTextViewEdit.setOnClickListener {
            showDialog(UpdateField.UpdateName()) { displayName ->
                viewModel.updateUserProfile(displayName = displayName) { success ->
                    if (success)
                        binding.userNameTextView.text = displayName
                }
            }
        }

        binding.userImageEdit.setOnClickListener {

            showDialog(UpdateField.UpdateImage()) { imageUrl ->
                viewModel.updateUserProfile(imageUrl = imageUrl) { success ->
                    if (success)
                        Glide
                            .with(view)
                            .load(imageUrl)
                            .into(binding.userImage)
                }
            }
        }
    }

    private fun showDialog(field: UpdateField, updateCallback: (url: String) -> Unit) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(field.title)

        val input = EditText(requireContext())

        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which ->
            try {
                input.text.toString().let { input ->
                    when (field) {
                        is UpdateField.UpdateName -> kotlin.run {
                            updateCallback(input)
                        }
                        is UpdateField.UpdateImage -> {
                            if (URLUtil.isValidUrl(input) && Patterns.WEB_URL.matcher(input)
                                    .matches()
                            ) {
                                updateCallback(input)
                            } else {
                                throw MalformedURLException()
                            }
                        }
                    }
                }
                input.hint = field.hint

            } catch (exception: Exception) {
                view?.let {
                    Snackbar.make(it, "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }

        }

        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    private val onMovieClick: (Int) -> Unit = { movieId ->
        viewModel.removeFavorite(movieId)
    }

    private fun initFavoritesAdapter() {
        favoriteMoviesAdapter = FavoriteMovieCarouselAdapter(
            tmdbImageManager.getLatestImageProvider(),
            onMovieClick,
        )
        binding.favoriteMoviesView.recyclerView.run {
            adapter = favoriteMoviesAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val itemSpacing =
                resources.getDimension(com.example.core_ui.R.dimen.spacing_normal).toInt()
            addItemDecoration(
                SpaceItemDecoration(
                    itemSpacing, -itemSpacing
                )
            )
        }

        binding.favoriteMoviesView.title.text = "Favorite Movies"
    }


    sealed class UpdateField(open val title: String, open val hint: String) {
        data class UpdateName(
            override val title: String = "Display Name",
            override val hint: String = "Type your name"
        ) : UpdateField(title, hint)

        data class UpdateImage(
            override val title: String = "Image URL",
            override val hint: String = "Update your image Url"
        ) : UpdateField(title, hint)
    }
}