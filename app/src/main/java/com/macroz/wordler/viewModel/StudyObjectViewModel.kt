package com.macroz.wordler.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.macroz.wordler.data.StudyObject
import com.macroz.wordler.data.StudyObjectRepository
import com.macroz.wordler.data.MyValues
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class StudyObjectViewModel(private val repository: StudyObjectRepository) : ViewModel() {

    var decksData: MutableList<MyValues> = repository.decksData

    fun updateDecksData(){
        repository.updateDecksData()
        decksData = repository.decksData
    }

    fun getDeckValues(deckName: String): MyValues{
        return repository.getDeckValues(deckName)
    }

    fun resetDeck(deckName: String){
        repository.resetDeck(deckName)
    }

    fun getAllCardsInDeck(deckName: String): List<StudyObject>{
        return repository.getAllCardsInDeck(deckName)
    }

    fun insert(studyObject: StudyObject) = viewModelScope.launch {
        repository.insert(studyObject)
    }
}

class StudyObjectViewModelFactory(private val repository: StudyObjectRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StudyObjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudyObjectViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}