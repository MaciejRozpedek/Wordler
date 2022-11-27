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
import androidx.navigation.NavOptions
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
        val deckName = arguments?.getString("deckName")!!


        binding.showWordListButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("deckName", deckName)

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
                        prefs.setNumOfNewCards(deckName, value)
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
        val startStudyingButton: Button = requireView().findViewById(R.id.startStudyingButton)

        newCardsLearned.text =
            "New Cards Learned: ${deckValues.numberOfCardsLearned} / ${deckValues.numberOfCardsInDeck}"
        progressBarInfo.progress =
            100 * deckValues.numberOfCardsLearned / deckValues.numberOfCardsInDeck
        newCardsTextView.text = "New Cards Today: ${prefs.getNumOfNewCardsLeft(deckName)}"
        reviewCardsTextView.text = "Review Cards Today: ${deckValues.numOfCardsInSession}"
        if(m.studyObjectViewModel.getNumOfCardsInSession(deckName) == 0){
            startStudyingButton.text = "GET MORE CARDS"
            binding.startStudyingButton.setOnClickListener {
                startStudyingButton.text = "START STUDYING"
                prefs.nextSession(deckName)
                m.studyObjectViewModel.updateNumOfCardsInSession(deckName)
                m.studyObjectViewModel.updateDecksData()
                findNavController().navigate( // recreate this
                    R.id.SetInfoScreenFragment,
                    arguments,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.SetInfoScreenFragment, true)
                        .build()
                )
                binding.startStudyingButton.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("deckName", deckName)

                    val navController: NavController = findNavController()
                    navController.navigate(R.id.action_SetInfoScreen_to_LearningScreen, bundle)
                }
            }
        } else {
            binding.startStudyingButton.setOnClickListener {
                m.studyObjectViewModel.updateNumOfCardsInSession(deckName)
                val bundle = Bundle()
                bundle.putString("deckName", deckName)

                val navController: NavController = findNavController()
                navController.navigate(R.id.action_SetInfoScreen_to_LearningScreen, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}