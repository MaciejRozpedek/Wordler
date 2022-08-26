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
        val editWordView = view.findViewById<EditText>(R.id.edit_group_name)

        binding.buttonSave.setOnClickListener {
            if(TextUtils.isEmpty(editWordView.text)) {
                Toast.makeText(
                    m.applicationContext,
                    "Word not saved because it is empty.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val groupName =editWordView.text.toString()
                val studyObject = StudyObject(
                    0,
                    0,
                    groupName,
                    "MAIN",
                    "MAIN_DESCRIPTION",
                    "SUBS",
                    "SUBS_DESCRIPTION"
                )
                if(studyObject.wordGroupName == "cos"){ // for testing
                    repeat(10){
                        m.studyObjectViewModel.insert(
                            StudyObject(
                                it + 732100, -1, "cos",
                                "m", "md", "s", "sd"
                            )
                        )
//                        m.studyObjectViewModel.insert(studyObject)
                    }
                    studyObject.sessionNumber = 1
                    repeat(7){
                        m.studyObjectViewModel.insert(
                            StudyObject(
                                it + 1000001, 0, "cos",
                                "m", "md", "s", "sd"
                            )
                        )
//                        m.studyObjectViewModel.insert(studyObject)
                    }
                    repeat(3){
                        m.studyObjectViewModel.insert(
                            StudyObject(
                                it + 2000001, 1, "cos",
                                "m", "md", "s", "sd"
                            )
                        )
//                        m.studyObjectViewModel.insert(studyObject)
                    }
                } else
                m.studyObjectViewModel.insert(studyObject)
            }
            findNavController().navigate(R.id.action_addGroupFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}