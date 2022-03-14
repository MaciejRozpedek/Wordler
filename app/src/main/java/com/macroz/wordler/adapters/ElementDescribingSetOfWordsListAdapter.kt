package com.macroz.wordler.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.macroz.wordler.viewModel.StudyObjectViewHolder

class ElementDescribingSetOfWordsListAdapter : ListAdapter<String, StudyObjectViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyObjectViewHolder {
        return StudyObjectViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudyObjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}