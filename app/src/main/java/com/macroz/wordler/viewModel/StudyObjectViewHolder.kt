package com.macroz.wordler.viewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.macroz.wordler.R

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