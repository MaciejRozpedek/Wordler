package com.macroz.wordler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.macroz.wordler.databinding.SetInfoScreenBinding

class SetInfoScreen : Fragment() {
    private var _binding: SetInfoScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SetInfoScreenBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            arguments?.getString("deckName")

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newCardsLearned: TextView = view.findViewById(R.id.newCardsLearned)
        val progressBarInfo: ProgressBar = view.findViewById(R.id.progressBarInfo)
        val newCardsTextView: TextView = view.findViewById(R.id.newCardsTextView)
        val reviewCardsTextView: TextView = view.findViewById(R.id.reviewCardsTextView)

        newCardsLearned.text = "New Cards Learned: ${
            arguments?.getInt("numOfCardsLearned")
        } / ${
            arguments?.getInt("numOfCardsInDeck")
        }"
        progressBarInfo.progress = arguments?.getInt("progress")!!
        newCardsTextView.text = "New Cards Today: ${arguments?.getInt("numOfNewCardsLeft")}"
        reviewCardsTextView.text = "Review Cards Today: ${arguments?.getInt("numOfCards")}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}