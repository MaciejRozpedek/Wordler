package com.macroz.wordler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.macroz.wordler.databinding.LearningScreenBinding

class LearningScreen: Fragment() {
    private lateinit var m: MainActivity
    private var _binding: LearningScreenBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        m = activity as MainActivity

        _binding = LearningScreenBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            arguments?.getString("deckName")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionInfoTextView: TextView = view.findViewById(R.id.sessionInfoTextView)
        val progressBarLearn: ProgressBar = view.findViewById(R.id.progressBarLearn)
        val mainWordTextView: TextView = view.findViewById(R.id.mainWordTextView)
        val deckName: String = arguments?.getString("deckName")!!
        val numOfCardsInSession: Int = m.studyObjectViewModel.recoverNumOfCardsInSession(deckName)
        val learnedInSession: Int = m.studyObjectViewModel.getNumOfCardsLearnedInSession(deckName)
        sessionInfoTextView.text = "Finished $learnedInSession out of $numOfCardsInSession cards"
        progressBarLearn.progress = learnedInSession * 100 / (numOfCardsInSession + 1)

        binding.showAnswerButton.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}