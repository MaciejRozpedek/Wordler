package com.macroz.wordler.viewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.macroz.wordler.R
import com.macroz.wordler.databinding.SessionEndScreenBinding

class SessionEndScreen: Fragment() {
    private var _binding: SessionEndScreenBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SessionEndScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val endOfSessionButton: Button = view.findViewById(R.id.endOfSessionButton)

        endOfSessionButton.setOnClickListener {
            val navController: NavController = findNavController()
            navController.navigate(R.id.action_sessionEndScreen_to_SetInfoScreen)
        }
    }
}