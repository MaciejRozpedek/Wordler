package com.macroz.wordler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.macroz.wordler.R
import com.macroz.wordler.adapters.ElementDescribingSetOfWordsListAdapter.StudyObjectViewHolder
import com.macroz.wordler.data.StudyObject

class ElementDescribingSetOfWordsListAdapter : ListAdapter<StudyObject, StudyObjectViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyObjectViewHolder {
        return StudyObjectViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudyObjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.wordGroupName)
    }

    class StudyObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name_of_set_of_words)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): StudyObjectViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.element_describing_set_of_words, parent, false)
                return StudyObjectViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<StudyObject>() {
            override fun areItemsTheSame(oldItem: StudyObject, newItem: StudyObject): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: StudyObject, newItem: StudyObject): Boolean {
                return oldItem.wordGroupName == newItem.wordGroupName
            }
        }
    }
}