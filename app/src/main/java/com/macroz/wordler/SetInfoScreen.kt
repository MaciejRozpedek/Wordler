package com.macroz.wordler

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
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
        val startStudyingButton: Button = view.findViewById(R.id.startStudyingButton)
        val deckName = arguments?.getString("deckName")!!

        newCardsLearned.text = "New Cards Learned: ${
            arguments?.getInt("numOfCardsLearned")
        } / ${
            arguments?.getInt("numOfCardsInDeck")
        }"
        progressBarInfo.progress = arguments?.getInt("progress")!!
        newCardsTextView.text = "New Cards Today: ${arguments?.getInt("numOfNewCardsLeft")}"
        reviewCardsTextView.text = "Review Cards Today: ${arguments?.getInt("numOfCardsInSession")}"

// this will be called when creating deck, but for now it stays here TODO()
        prefs.setNumOfNewCards(deckName, 10)
        m.studyObjectViewModel.updateNumOfCardsInSession(deckName)
// END

        if(m.studyObjectViewModel.getNumOfCardsInSession(deckName) == 0){
            startStudyingButton.text = "GET MORE CARDS"
            binding.startStudyingButton.setOnClickListener {
                startStudyingButton.text = "START STUDYING"
                prefs.nextSession(deckName)
                m.studyObjectViewModel.updateNumOfCardsInSession(deckName)
                binding.startStudyingButton.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("deckName", arguments?.getString("deckName"))

                    val navController: NavController = findNavController()
                    navController.navigate(R.id.action_SetInfoScreen_to_LearningScreen, bundle)
                }
            }
        } else {
            binding.startStudyingButton.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("deckName", arguments?.getString("deckName"))

                val navController: NavController = findNavController()
                navController.navigate(R.id.action_SetInfoScreen_to_LearningScreen, bundle)
            }
        }

        binding.showWordListButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("deckName", arguments?.getString("deckName"))

            val navController: NavController = findNavController()
            navController.navigate(R.id.action_SetInfoScreen_to_WordListScreen, bundle)
        }

        binding.changeNewCardsPerDayButton.setOnClickListener {
            val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
            alert.setTitle("New Cards Each Day")
//            alert.setMessage("Message")
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
                        Toast.makeText(
                            m.applicationContext,
                            "Changed successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        onResume()
                    } else {
                        Toast.makeText(
                            m.applicationContext,
                            "Changing new cards per day limit failed.",
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

        binding.resetButton.setOnClickListener {
            val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
            alert.setTitle("Reset this deck?")
            alert.setMessage("You are about to reset all progress on this deck. To continue, type" +
                    " \"RESET\" and choose \"OK\"")
            // Set an EditText view to get user input\
            val input = EditText(context)
            alert.setView(input)
            alert.setPositiveButton("OK") { _, _ ->
                val pom: String = input.text.toString()
                if (pom == "RESET") {
                    m.studyObjectViewModel.resetDeck(requireArguments().getString("deckName")!!)
                    Toast.makeText(
                        m.applicationContext,
                        "Deck successfully reset",
                        Toast.LENGTH_LONG
                    ).show()
                    onResume()
                } else {
                    Toast.makeText(
                        m.applicationContext,
                        "Resetting deck failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
                // Do something with value!
            }

            alert.setNegativeButton("Cancel"
            ) { _, _ ->
                // Canceled.
            }

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