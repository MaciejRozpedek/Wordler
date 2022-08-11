package com.macroz.wordler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.macroz.wordler.R
import com.macroz.wordler.adapters.ElementDescribingSetOfWordsListAdapter.StudyObjectViewHolder
import com.macroz.wordler.data.myValues

class ElementDescribingSetOfWordsListAdapter : ListAdapter<myValues, StudyObjectViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyObjectViewHolder {
        return StudyObjectViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudyObjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.nameOfSetOfWords, current.numberOfCards, current.numberOfCardsLearned)
    }

    class StudyObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.nameOfSetOfWords)
        private val progressInNumbers: TextView = itemView.findViewById(R.id.progressInNumbers)
        private val progressBarMain: ProgressBar = itemView.findViewById(R.id.progressBarMain)
        private val cardsDueToday: TextView = itemView.findViewById(R.id.cardsDueToday)

        fun bind(nameOfSetOfWords: String?, numberOfCardsInDeck: Int, numberOfCardsLearned: Int) {
            wordItemView.text = nameOfSetOfWords
            progressInNumbers.text = "$numberOfCardsLearned out of $numberOfCardsInDeck cards learned"
            progressBarMain.progress = 100*numberOfCardsLearned/numberOfCardsInDeck
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
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<myValues>() {
            override fun areItemsTheSame(oldItem: myValues, newItem: myValues): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: myValues, newItem: myValues): Boolean {
                return oldItem.nameOfSetOfWords == newItem.nameOfSetOfWords
            }
        }
    }
}