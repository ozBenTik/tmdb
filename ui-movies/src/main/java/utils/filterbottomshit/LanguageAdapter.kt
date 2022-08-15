package utils.filterbottomshit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.core.util.SupportedLanguages
import com.example.ui_movies.R

class LanguageAdapter(
    context: Context,
    resource: Int,
    private val textViewResourceId: Int,
    private val items: List<SupportedLanguages>
) :
    ArrayAdapter<SupportedLanguages>(context, resource, items) {

    val suggestions = mutableListOf<SupportedLanguages>()
    val tempItems = mutableListOf<SupportedLanguages>().apply {
        addAll(items)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater)?.let { inflater ->
                view = inflater.inflate(R.layout.language_list_item, parent, false)
            }
        }

        return view!!.apply {
            items.takeIf { it.isNotEmpty() }?.let { items ->
                items[position].let { item ->
                    findViewById<TextView>(textViewResourceId)?.text = item.code
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return (resultValue as? SupportedLanguages)?.description ?: ""
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                return FilterResults().apply {
                    constraint?.let { searchConstraint ->
                        suggestions.clear()
                        tempItems.forEach { item ->
                            if (item.code.lowercase()
                                    .contains("$searchConstraint".lowercase())
                            ) {
                                suggestions.add(item)
                            }
                        }
                        values = suggestions
                        count = suggestions.size
                    }
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.takeIf { it.count > 0 }?.let {
                    clear()
                    (results.values as? MutableList<SupportedLanguages>)?.forEach { result ->
                        add(result)
                        notifyDataSetChanged()
                    }
                }
            }

        };
    }
}