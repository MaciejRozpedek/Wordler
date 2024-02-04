package com.macroz.wordler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.macroz.wordler.data.StudyObject
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
        println("OnCreateView called")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainWordTextView: TextView = view.findViewById(R.id.mainWordTextView)

        val deckName: String = arguments?.getString("deckName")!!
        val studyObject: StudyObject? = m.studyObjectViewModel.getStudyObject(deckName)
        if (studyObject != null) {
            mainWordTextView.text = "${studyObject.mainWord} (${studyObject.mainWordDescription})"
            binding.showAnswerButton.setOnClickListener {
                val bundle = Bundle()
                bundle.putAll(arguments)
                bundle.putInt("studyObject_Id", studyObject.id)

                val navController: NavController = findNavController()
                if (studyObject.lastWaitingTime < 0) {
                    navController.navigate(R.id.action_LearningScreen_to_AnswerScreen3, bundle)
                } else {
                    navController.navigate(R.id.action_LearningScreen_to_AnswerScreen4, bundle)
                }
            }
        } else {
            println("Number of cards in session equals 0")
            val navController: NavController = findNavController()
            navController.navigate(R.id.action_LearningScreen_to_SetInfoScreen)
//            TODO("There are no cards in session. Add Ended session screen")
        }
        println("OnViewCreated called.")
    }

    override fun onResume() {
        super.onResume()
        val deckName: String = arguments?.getString("deckName")!!
        val sessionInfoTextView: TextView = requireView().findViewById(R.id.sessionInfoTextView)
        val progressBarLearn: ProgressBar = requireView().findViewById(R.id.progressBarLearn)
        val numOfCardsInSession: Int = m.studyObjectViewModel.recoverNumOfCardsInSession(deckName)
        val learnedInSession: Int = m.studyObjectViewModel.getNumOfCardsLearnedInSession(deckName)
        sessionInfoTextView.text = "Finished $learnedInSession out of $numOfCardsInSession cards"
        progressBarLearn.progress = learnedInSession * 100 / (numOfCardsInSession + 1)
        println("Resumed learning screen.")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}