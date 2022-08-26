package com.macroz.wordler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.macroz.wordler.adapters.WordListElementAdapter
import com.macroz.wordler.data.StudyObject
import com.macroz.wordler.databinding.SetsOverviewScreenBinding

class WordListScreen : Fragment() {
    private lateinit var m: MainActivity
    private var _binding: SetsOverviewScreenBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        m = activity as MainActivity

        _binding = SetsOverviewScreenBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            arguments?.getString("deckName")
        val recyclerView = binding.recyclerviewOfGroups
        val adapter = WordListElementAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val deckName: String = arguments?.getString("deckName")!!
        val allCardsInDeck: List<StudyObject> = m.studyObjectViewModel.getAllCardsInDeck(deckName)
        adapter.submitList(allCardsInDeck)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}