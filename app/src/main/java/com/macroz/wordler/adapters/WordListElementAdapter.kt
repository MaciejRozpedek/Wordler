package com.macroz.wordler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.macroz.wordler.R
import com.macroz.wordler.adapters.WordListElementAdapter.WordViewHolder
import com.macroz.wordler.data.StudyObject

class WordListElementAdapter :
    ListAdapter<StudyObject, WordViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            current.mainWord, current.answerWord
        )
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mainWordTextView: TextView = itemView.findViewById(R.id.mainWord)
        private val subsWordTextView: TextView = itemView.findViewById(R.id.subsidiaryWord)

        fun bind(
            mainWord: String, subsidiaryWord: String
        ) {
            mainWordTextView.text = mainWord
            subsWordTextView.text = subsidiaryWord
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.word_list_element, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<StudyObject>() {
            override fun areItemsTheSame(oldItem: StudyObject, newItem: StudyObject): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: StudyObject, newItem: StudyObject): Boolean {
                return if (oldItem.mainWord == newItem.mainWord) {
                    oldItem.answerWord == newItem.answerWord
                } else {
                    oldItem.mainWord == newItem.mainWord
                }
            }
        }
    }
}