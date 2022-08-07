package filterbottomshit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.model.FilterKey
import com.example.model.FilterParams
import com.example.ui_movies.R
import com.example.ui_movies.databinding.FilterBottomShitBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class FiltersBottomShit(
    private val currentParams: FilterParams,
    val onSubmit: (params: FilterParams) -> Unit
) : BottomSheetDialogFragment() {

    lateinit var binding: FilterBottomShitBinding
    private val viewModel: FilterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterBottomShitBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun updateFilters(filterParams: FilterParams) {
        viewModel.filterParams = filterParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!currentParams.isEmpty()) {
            updateFilters(currentParams)
        }

        binding.cancelFilter.setOnClickListener {
            dismiss()
        }

        binding.submitButton.setOnClickListener {
            onSubmit( viewModel.filterParams)
            dismissAllowingStateLoss()
        }

        binding.toDatePicker.setOnClickListener {
            showDatePicker( viewModel.filterParams.release_dateFrom.second) { mils, formatted ->
                binding.toDateTextView.text = formatted
                viewModel.applyFilter(FilterKey.RELEASE_DATE_TO, mils to formatted)
            }
        }

        binding.fromDatePicker.setOnClickListener {
            showDatePicker( viewModel.filterParams.release_dateFrom.second) { mils, formatted ->
                binding.fromDateTextView.text = formatted
                viewModel.applyFilter(FilterKey.RELEASE_DATE_FROM, mils to formatted)
            }
        }

        initLanguageField(
            viewModel.filterParams.language,
            binding.languageInput,
            listOf(
                "" to "",
                "Hebrew" to "he-il",
                "English" to "en-us"
            )
        )

        launchAndRepeatWithViewLifecycle {
            viewModel.genres.collectLatest { generes ->
                binding.genresLoadingView.visibility = View.GONE
                binding.genresChipGroup.removeAllViews()
                generes?.forEach { genre ->
                    genre.name?.let { genreName ->
                        binding.genresChipGroup.addView(
                            Chip(context).apply {
                                text = genreName
                                id = genre.id!!
                                isCheckable = true
                                isChecked =  viewModel.filterParams.genres.contains(genre)
                                setOnClickListener {
                                    if ((it as Chip).isChecked) {
                                        viewModel.applyFilter(FilterKey.GENRES, genre)
                                    } else {
                                        viewModel.removeFilter(FilterKey.GENRES, id)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun initLanguageField(
        initialValue: String,
        container: AutoCompleteTextView,
        items: List<Pair<String, String>>
    ) {
        container.apply {
            setAdapter(
                LanguageAdapter(
                    requireContext(),
                    R.layout.filter_bottom_shit,
                    R.id.language_label,
                    items
                )
            )

            items.forEachIndexed { index, langPair ->
                if (langPair.second == initialValue) {
                    setSelection(index)
                    return
                }
            }

            setOnItemClickListener { adapterView, view, pos, id ->
                ((adapterView.getItemAtPosition(pos) as? Pair<String, String>)?.second)?.let {
                    if (it.isEmpty()) {
                        binding.languageInput.clearListSelection()
                    }
                    viewModel.applyFilter(
                        FilterKey.LANGUAGE, it
                    )
                    binding.languageInput.clearFocus()
                }
            }
        }
    }

    private fun showDatePicker(initialValue: Long, onDateSelected: (mills: Long, toString: String) -> Unit) {

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTheme(com.example.core_ui.R.style.DatePickerStyle)
                .setTitleText("Select a date")
                .setSelection(
                    initialValue.takeIf { it > 0 } ?: MaterialDatePicker.todayInUtcMilliseconds()
                )
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val calender = Calendar.getInstance()
            calender.timeInMillis = it
            onDateSelected(it, formatter.format(calender.time).toString())
            datePicker.dismiss()
        }

        datePicker.show(parentFragmentManager, "")
    }

    companion object {
        const val TAG = "FilterBottomSheet"
    }
}