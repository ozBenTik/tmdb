package utils.movies_filter_bottomshit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.model.FilterKey
import com.example.model.FilterParams
import com.example.ui_movies.R
import com.example.ui_movies.databinding.FilterBottomShitBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class FiltersBottomShit(
    private val currentParams: FilterParams,
) : BottomSheetDialogFragment() {

    constructor() : this(FilterParams())

    lateinit var binding: FilterBottomShitBinding
    private val viewModel: FilterViewModel by viewModels()

    private val filterParams: FilterParams
    get() = viewModel.filterParams

    var onSubmit: (params: FilterParams) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterBottomShitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!currentParams.isEmpty()) {
            viewModel.filterParams = currentParams
        }

        binding.cancelFilter.setOnClickListener {
            dismiss()
        }

        binding.submitButton.setOnClickListener {
            onSubmit(filterParams)
            dismiss()
        }

        binding.toDateTextView.text = filterParams.release_dateTo.first
        binding.fromDateTextView.text = filterParams.release_dateFrom.first

        binding.toDatePicker.setOnClickListener {
            showDatePicker(
                filterParams.release_dateTo.second,
                binding.toDateTextView,
                FilterKey.RELEASE_DATE_TO
            ) { mils, formatted ->
                viewModel.addFilter(FilterKey.RELEASE_DATE_TO, formatted to mils )
            }
        }

        binding.fromDatePicker.setOnClickListener {
            showDatePicker(
                filterParams.release_dateFrom.second,
                binding.fromDateTextView,
                FilterKey.RELEASE_DATE_FROM
            ) { mils, formatted ->
                viewModel.addFilter(FilterKey.RELEASE_DATE_FROM, formatted to mils )
            }
        }

        initLanguageField(
            filterParams.language,
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

                val checkedGenres = filterParams.genres

                generes?.forEach { genre ->
                    genre.name?.let { genreName ->
                        binding.genresChipGroup.addView(
                            Chip(context).apply {
                                text = genreName
                                id = genre.id!!
                                isCheckable = true
                                isChecked = checkedGenres.contains(genre)
                                setOnClickListener {
                                    if ((it as Chip).isChecked) {
                                        viewModel.addFilter(FilterKey.GENRES, genre)
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
                    viewModel.addFilter(
                        FilterKey.LANGUAGE, it
                    )
                    binding.languageInput.clearFocus()
                }
            }
        }
    }

    private fun showDatePicker(
        initialValue: Long,
        presenterTextView: TextView,
        source: FilterKey,
        onDateSelected: (mills: Long, formatted: String) -> Unit
    ) {

        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val calender = Calendar.getInstance()
        calender.timeInMillis =
            initialValue.takeIf { it > 0 } ?: MaterialDatePicker.todayInUtcMilliseconds()
        presenterTextView.text = formatter.format(calender.time)

        MaterialDatePicker.Builder.datePicker()
            .setTheme(com.example.core_ui.R.style.DatePickerStyle)
            .setTitleText("Select a date")
            .setSelection(calender.timeInMillis)
            .build().let { datePicker ->

                datePicker.addOnPositiveButtonClickListener { dateValue ->

                    source.takeIf { it == FilterKey.RELEASE_DATE_TO && filterParams.release_dateFrom.second > dateValue}?.run {
                        Toast.makeText(requireContext(), "Invalid input - To cannot be before from", Toast.LENGTH_SHORT).show()
                        presenterTextView.text = ""
                    } ?: kotlin.run {
                        calender.timeInMillis = dateValue
                        formatter.format(calender.time).let {  formatted ->
                            onDateSelected(dateValue, formatted)
                            presenterTextView.text = formatted
                        }
                    }
                    datePicker.dismiss()
                }

                datePicker.show(parentFragmentManager, DATE_PICKER_TAG)
            }
    }

    companion object {
        const val TAG = "FilterBottomSheet"
        const val DATE_PICKER_TAG = "DatePicker"
    }
}