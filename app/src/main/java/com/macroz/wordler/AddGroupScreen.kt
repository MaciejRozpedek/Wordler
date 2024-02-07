package com.macroz.wordler

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.macroz.wordler.data.StudyObject
import com.macroz.wordler.databinding.AddGroupScreenBinding

class AddGroupScreen : Fragment() {
    private lateinit var m: MainActivity

    private var _binding: AddGroupScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        m = activity as MainActivity
        _binding = AddGroupScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextDeckName = view.findViewById<EditText>(R.id.editTextDeckName)
        val editTextQuestion = view.findViewById<EditText>(R.id.editTextQuestion)
        val editTextQuestionDescription = view.findViewById<EditText>(R.id.editTextQuestionDescription)
        val editTextAnswer = view.findViewById<EditText>(R.id.editTextAnswer)
        val editTextAnswerDescription = view.findViewById<EditText>(R.id.editTextAnswerDescription)

        binding.buttonSave.setOnClickListener {
            if(TextUtils.isEmpty(editTextDeckName.text)) {
                Toast.makeText(
                    m.applicationContext,
                    "Deck name field cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (TextUtils.isEmpty(editTextQuestion.text)) {
                Toast.makeText(
                    m.applicationContext,
                    "Question field cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (TextUtils.isEmpty(editTextAnswer.text)) {
                Toast.makeText(
                    m.applicationContext,
                    "Answer field cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val deckName = editTextDeckName.text.toString()
                val question = editTextQuestion.text.toString()
                val questionDescription = editTextQuestionDescription.text.toString()
                val answer = editTextAnswer.text.toString()
                val answerDescription = editTextAnswerDescription.text.toString()

                val studyObject = StudyObject(
                    0,
                    (0..Long.MAX_VALUE).random(),
                    -1,
                    deckName,
                    question,
                    questionDescription,
                    answer,
                    answerDescription,
                    -1
                )
                m.studyObjectViewModel.insert(studyObject)
                findNavController().navigate(R.id.action_addGroupFragment_to_FirstFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}