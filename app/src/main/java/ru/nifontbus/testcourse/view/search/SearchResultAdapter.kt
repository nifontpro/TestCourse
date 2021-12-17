package ru.nifontbus.testcourse.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.nifontbus.testcourse.databinding.ListItemBinding
import ru.nifontbus.testcourse.model.SearchResult
import ru.nifontbus.testcourse.view.search.SearchResultAdapter.SearchResultViewHolder

internal class SearchResultAdapter : RecyclerView.Adapter<SearchResultViewHolder>() {

    private var results: List<SearchResult> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchResultViewHolder(
            ListItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: SearchResultViewHolder,
        position: Int
    ) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateResults(results: List<SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }

    internal class SearchResultViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchResult: SearchResult) {
            binding.repositoryName.text = searchResult.fullName
            binding.repositoryName.setOnClickListener {
                Toast.makeText(itemView.context, searchResult.fullName, Toast.LENGTH_SHORT).show()
            }

        }
    }
}
