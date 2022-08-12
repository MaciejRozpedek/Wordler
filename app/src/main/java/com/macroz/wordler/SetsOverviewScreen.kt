package com.macroz.wordler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.observe
import com.macroz.wordler.adapters.ElementDescribingSetOfWordsListAdapter
import com.macroz.wordler.databinding.SetsOverviewScreenBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SetsOverviewScreen : Fragment() {
    private lateinit var m: MainActivity
    private var _binding: SetsOverviewScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        m = activity as MainActivity

        _binding = SetsOverviewScreenBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerviewOfGroups
        val adapter = ElementDescribingSetOfWordsListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter.submitList(m.studyObjectViewModel.decksData)


        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AddGroupFragment)
        }
        view.findViewById<TextView>(R.id.motivatingText).text = "You're the best"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}