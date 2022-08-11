package com.macroz.wordler.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.macroz.wordler.data.StudyObject
import com.macroz.wordler.data.StudyObjectRepository
import com.macroz.wordler.data.myValues
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class StudyObjectViewModel(private val repository: StudyObjectRepository) : ViewModel() {

    var decksData: MutableList<myValues> = repository.decksData

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