package com.macroz.wordler.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.macroz.wordler.data.StudyObject
import com.macroz.wordler.data.StudyObjectRepository
import com.macroz.wordler.data.MyValues
import com.macroz.wordler.prefs
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import kotlin.math.abs
import kotlin.random.Random

class StudyObjectViewModel(private val repository: StudyObjectRepository) : ViewModel() {

    var decksData: MutableList<MyValues> = repository.decksData
    var sessionCards: MutableMap<String, MutableList<StudyObject>> = mutableMapOf()

    fun updateDecksData() {
        repository.updateDecksData()
        decksData = repository.decksData
    }

    fun updateNumOfCardsInSession(deckName: String) {
        repository.updateNumOfCardsInSession(deckName)
    }

    fun getNumOfCardsInSession(deckName: String): Int {
        return repository.getNumOfCardsInSession(deckName)
    }

    fun recoverNumOfCardsInSession(deckName: String): Int {
        return repository.recoverNumOfCardsInSession(deckName)
    }

    fun getNumOfCardsLearnedInSession(deckName: String): Int {
        return repository.getNumOfCardsLearnedInSession(deckName)
    }

    fun getDeckValues(deckName: String): MyValues {
        return repository.getDeckValues(deckName)
    }

    fun resetDeck(deckName: String) {
        repository.resetDeck(deckName)
    }

    fun getAllCardsInDeck(deckName: String): List<StudyObject> {
        return repository.getAllCardsInDeck(deckName)
    }

    fun getStudyObject(deckName: String): StudyObject? {
        if (sessionCards.containsKey(deckName)) {
            if (sessionCards[deckName]!!.size == 0) {
                println("sessionCards[$deckName] is empty.")
                return null
            }
            if(prefs.isNumOfNewCardsChanged(deckName)) {
                val numOfNewCards: Int = prefs.getNumOfNewCards(deckName)
                val lastNumOfNewCards: Int = prefs.getLastNumOfNewCards(deckName)
                prefs.setLastNumOfNewCards(deckName, numOfNewCards)
                val diff: Int = numOfNewCards - lastNumOfNewCards
                if (diff < 0) {
                    repeat(abs(diff)) {
                        sessionCards[deckName]?.removeLast()
                    }
                } else {
                    sessionCards[deckName]!!.addAll(repository.getSession(-1, deckName, diff))
                }
            }
        } else {
            sessionCards[deckName] = mutableListOf()
            val sessionNum: Int = prefs.getSessionNum(deckName)
            val numOfNewCardsLeft = prefs.getNumOfNewCardsLeft(deckName)
            sessionCards[deckName]!!.addAll(repository.getSession(sessionNum, deckName))
            sessionCards[deckName]!!.addAll(repository.getSession(-1, deckName, numOfNewCardsLeft))
        }
        return sessionCards[deckName]!![Random.nextInt(sessionCards[deckName]!!.size)]
    }

    fun getStudyObject(Id: Int): StudyObject {
        return repository.getStudyObject(Id)
    }

    fun insert(studyObject: StudyObject) = viewModelScope.launch {
        repository.insert(studyObject)
    }

    fun insertAndReplace(studyObject: StudyObject) = viewModelScope.launch {
        prefs.changeNumOfNewCardsLeft(studyObject.wordGroupName, -1)
        sessionCards[studyObject.wordGroupName]?.removeAll { it.id == studyObject.id }
        repository.insertAndReplace(studyObject)
    }
}

class StudyObjectViewModelFactory(private val repository: StudyObjectRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyObjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudyObjectViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}