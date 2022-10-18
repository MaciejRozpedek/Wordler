package com.macroz.wordler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.macroz.wordler.databinding.AnswerScreen4Binding

class AnswerScreen4: Fragment() {
    private var _binding: AnswerScreen4Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AnswerScreen4Binding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            arguments?.getString("deckName")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionInfoTextView: TextView = view.findViewById(R.id.sessionInfoTextView4)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBarAnswer4)
        val mainWordTextView: TextView = view.findViewById(R.id.mainWordTextView4)
        val answerTextView: TextView = view.findViewById(R.id.answerTextView4)
        val answerButtonOne: Button = view.findViewById(R.id.answerButtonOne4)
        val answerButtonTwo: Button = view.findViewById(R.id.answerButtonTwo4)
        val answerButtonThree: Button = view.findViewById(R.id.answerButtonThree4)
        val answerButtonFour: Button = view.findViewById(R.id.answerButtonFour4)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}