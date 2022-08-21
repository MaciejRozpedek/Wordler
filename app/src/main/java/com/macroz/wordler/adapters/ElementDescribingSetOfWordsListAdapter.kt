package com.macroz.wordler.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.macroz.wordler.R
import com.macroz.wordler.adapters.ElementDescribingSetOfWordsListAdapter.StudyObjectViewHolder
import com.macroz.wordler.data.MyValues


class ElementDescribingSetOfWordsListAdapter :
    ListAdapter<MyValues, StudyObjectViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyObjectViewHolder {
        return StudyObjectViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudyObjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            current.nameOfSetOfWords,
            current.numberOfCardsInDeck,
            current.numberOfCardsLearned,
            current.numOfNewCardsLeft,
            current.numOfCardsInSession
        )
    }

    class StudyObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deckNameTextView: TextView = itemView.findViewById(R.id.nameOfSetOfWords)
        private val progressInNumbers: TextView = itemView.findViewById(R.id.progressInNumbers)
        private val progressBarMain: ProgressBar = itemView.findViewById(R.id.progressBarMain)
        private val cardsDueToday: TextView = itemView.findViewById(R.id.cardsDueToday)


        fun bind(
            nameOfDeck: String?, numOfCardsInDeck: Int, numOfCardsLearned: Int,
            numOfNewCardsLeft: Int, numOfCardsInSession: Int
        ) {
            deckNameTextView.text = nameOfDeck
            progressInNumbers.text = "$numOfCardsLearned out of $numOfCardsInDeck cards learned"
            progressBarMain.progress = 100 * numOfCardsLearned / numOfCardsInDeck
            cardsDueToday.text =
                "$numOfCardsInSession review card(s), $numOfNewCardsLeft new card(s) due today"
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("deckName", nameOfDeck)
                bundle.putInt("numOfCardsLearned", numOfCardsLearned)
                bundle.putInt("numOfCardsInDeck", numOfCardsInDeck)
                bundle.putInt("progress", progressBarMain.progress)
                bundle.putInt("numOfNewCardsLeft", numOfNewCardsLeft)
                bundle.putInt("numOfCardsInSession", numOfCardsInSession)

                val navController: NavController = Navigation.findNavController(itemView)
                navController.navigate(R.id.action_FirstFragment_to_SetInfoScreen, bundle)
                println("Just Clicked position number $adapterPosition!!!")
            }
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
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<MyValues>() {
            override fun areItemsTheSame(oldItem: MyValues, newItem: MyValues): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: MyValues, newItem: MyValues): Boolean {
                return oldItem.nameOfSetOfWords == newItem.nameOfSetOfWords
            }
        }
    }
}