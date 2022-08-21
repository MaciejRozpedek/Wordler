package com.macroz.wordler

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.macroz.wordler.data.MyValues
import com.macroz.wordler.databinding.SetInfoScreenBinding


class SetInfoScreen : Fragment() {
    private lateinit var m: MainActivity

    private var _binding: SetInfoScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        m = activity as MainActivity
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
        reviewCardsTextView.text = "Review Cards Today: ${arguments?.getInt("numOfCardsInSession")}"

        binding.changeNewCardsPerDayButton.setOnClickListener {
            val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
            alert.setTitle("New Cards Each Day")
//            alert.setMessage("Message")
            // Set an EditText view to get user input
            // Set an EditText view to get user input
            val input = EditText(context)
            alert.setView(input)
            alert.setPositiveButton("Set",
                DialogInterface.OnClickListener { dialog, whichButton ->
                    val pom: String = input.text.toString()
                    val value: Int? = pom.toIntOrNull()
                    if (value != null) {
                        arguments?.getString("deckName")
                            ?.let { it1 -> prefs.setNumOfNewCards(it1, value) }
                    } else {
                        Toast.makeText(
                            m.applicationContext,
                            "Word not saved because it is empty.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    // Do something with value!
                })

            alert.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, whichButton ->
                    // Canceled.
                })

            alert.show()
        }
    }

    override fun onResume() {
        super.onResume()
        val deckName = arguments?.getString("deckName")
        val deckValues: MyValues = m.studyObjectViewModel.getDeckValues(deckName!!)
        val newCardsLearned: TextView = requireView().findViewById(R.id.newCardsLearned)
        val progressBarInfo: ProgressBar = requireView().findViewById(R.id.progressBarInfo)
        val newCardsTextView: TextView = requireView().findViewById(R.id.newCardsTextView)
        val reviewCardsTextView: TextView = requireView().findViewById(R.id.reviewCardsTextView)

        newCardsLearned.text =
            "New Cards Learned: ${deckValues.numberOfCardsLearned} / ${deckValues.numberOfCardsInDeck}"
        progressBarInfo.progress =
            100 * deckValues.numberOfCardsLearned / deckValues.numberOfCardsInDeck
        newCardsTextView.text = "New Cards Today: ${deckValues.numOfNewCardsLeft}"
        reviewCardsTextView.text = "Review Cards Today: ${deckValues.numOfCardsInSession}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}