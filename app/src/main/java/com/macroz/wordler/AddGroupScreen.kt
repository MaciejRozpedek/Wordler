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
                    -1,
                    groupName,
                    "First Card",
                    "",
                    "",
                    ""
                )
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