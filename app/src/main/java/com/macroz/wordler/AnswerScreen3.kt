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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.macroz.wordler.data.StudyObject
import com.macroz.wordler.databinding.AnswerScreen3Binding

class AnswerScreen3: Fragment() {
    private lateinit var m: MainActivity
    private var _binding: AnswerScreen3Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        m = activity as MainActivity
        _binding = AnswerScreen3Binding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            arguments?.getString("deckName")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionInfoTextView: TextView = view.findViewById(R.id.sessionInfoTextView3)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBarAnswer3)
        val mainWordTextView: TextView = view.findViewById(R.id.mainWordTextView3)
        val answerTextView: TextView = view.findViewById(R.id.answerTextView3)
        val answerButtonOne: Button = view.findViewById(R.id.answerButtonOne3)
        val answerButtonTwo: Button = view.findViewById(R.id.answerButtonTwo3)
        val answerButtonThree: Button = view.findViewById(R.id.answerButtonThree3)

        val deckName: String = arguments?.getString("deckName")!!
        val numOfCardsInSession: Int = m.studyObjectViewModel.recoverNumOfCardsInSession(deckName)
        val learnedInSession: Int = m.studyObjectViewModel.getNumOfCardsLearnedInSession(deckName)
        val studyObjectId: Int = arguments?.getInt("studyObject_Id")!!
        val studyObject: StudyObject = m.studyObjectViewModel.getStudyObject(studyObjectId)
        val sessionNum: Int = prefs.getSessionNum(deckName)
        sessionInfoTextView.text = "Finished $learnedInSession out of $numOfCardsInSession cards"
        progressBar.progress = learnedInSession * 100 / (numOfCardsInSession + 1)
        mainWordTextView.text = "${studyObject.mainWord} (${studyObject.mainWordDescription})"
        answerTextView.text = "${studyObject.answerWord} (${studyObject.answerWordDescription})"
        answerButtonOne.text = "DIDN'T KNOW (SEE IT AGAIN)"
        answerButtonOne.setOnClickListener {
            val navController: NavController = findNavController()
            navController.navigate(R.id.action_AnswerScreen3_to_LearningScreen)
        }
        answerButtonTwo.text = "GOOD (IN A FEW MINUTES)"
        answerButtonTwo.setOnClickListener {
            val navController: NavController = findNavController()
            navController.navigate(R.id.action_AnswerScreen3_to_LearningScreen)
        }
        answerButtonThree.text = "EASY (1 DAY)"
        answerButtonThree.setOnClickListener {
            studyObject.sessionNumber = sessionNum + 1
            studyObject.lastWaitingTime = 1
            m.studyObjectViewModel.insertAndReplace(studyObject, true)
            val navController: NavController = findNavController()
            navController.navigate(R.id.action_AnswerScreen3_to_LearningScreen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}