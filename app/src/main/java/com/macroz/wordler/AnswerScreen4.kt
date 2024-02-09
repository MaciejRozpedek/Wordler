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
import com.macroz.wordler.databinding.AnswerScreen4Binding

class AnswerScreen4: Fragment() {
    private lateinit var m: MainActivity
    private var _binding: AnswerScreen4Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        m = activity as MainActivity
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

        val deckName: String = arguments?.getString("deckName")!!
        val numOfCardsInSession: Int = m.studyObjectViewModel.recoverNumOfCardsInSession(deckName)
        val learnedInSession: Int = m.studyObjectViewModel.getNumOfCardsLearnedInSession(deckName)
        val studyObjectId: Int = arguments?.getInt("studyObject_Id")!!
        val studyObject: StudyObject = m.studyObjectViewModel.getStudyObject(studyObjectId)
        val sessionNum: Int = prefs.getSessionNum(deckName)
        sessionInfoTextView.text = "Finished $learnedInSession out of $numOfCardsInSession cards"
        if (numOfCardsInSession == 0) {
            progressBar.progress = 100
        } else {
            progressBar.progress = learnedInSession * 100 / (numOfCardsInSession)
        }

        if (studyObject.mainWordDescription == null || studyObject.mainWordDescription == "") {
            mainWordTextView.text = studyObject.mainWord
        } else {
            mainWordTextView.text = "${studyObject.mainWord} (${studyObject.mainWordDescription})"
        }
        if (studyObject.answerWordDescription == null || studyObject.answerWordDescription == "") {
            answerTextView.text = studyObject.answerWord
        } else {
            answerTextView.text = "${studyObject.answerWord} (${studyObject.answerWordDescription})"
        }
        answerButtonOne.text = "DIDN'T KNOW (SEE IT AGAIN)"
        answerButtonOne.setOnClickListener {
            studyObject.lastWaitingTime = -2
            m.studyObjectViewModel.insertAndReplace(studyObject, false)
            val navController: NavController = findNavController()
            navController.navigate(R.id.action_AnswerScreen4_to_LearningScreen)
        }
        val waitingTime2: Int = studyObject.lastWaitingTime
        answerButtonTwo.text = "HARD ($waitingTime2 DAYS)"
        answerButtonTwo.setOnClickListener {
            studyObject.sessionNumber += waitingTime2
            studyObject.lastWaitingTime = waitingTime2
            m.studyObjectViewModel.insertAndReplace(studyObject, false)
            val navController: NavController = findNavController()
            navController.navigate(R.id.action_AnswerScreen4_to_LearningScreen)
        }
        val waitingTime3: Int = utensils.nextFibonacci(waitingTime2)
        answerButtonThree.text = "GOOD ($waitingTime3 DAYS)"
        answerButtonThree.setOnClickListener {
            studyObject.sessionNumber += waitingTime3
            studyObject.lastWaitingTime = waitingTime3
            m.studyObjectViewModel.insertAndReplace(studyObject, false)
            val navController: NavController = findNavController()
            navController.navigate(R.id.action_AnswerScreen4_to_LearningScreen)
        }
        val waitingTime4: Int = utensils.nextFibonacci(waitingTime3)
        answerButtonFour.text = "EASY ($waitingTime4 DAYS)"
        answerButtonFour.setOnClickListener {
            studyObject.sessionNumber += waitingTime4
            studyObject.lastWaitingTime = waitingTime4
            m.studyObjectViewModel.insertAndReplace(studyObject, false)
            val navController: NavController = findNavController()
            navController.navigate(R.id.action_AnswerScreen4_to_LearningScreen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}