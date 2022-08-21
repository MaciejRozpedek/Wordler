package com.macroz.wordler.data

import androidx.annotation.WorkerThread
import com.macroz.wordler.prefs

class MyValues(
    nameOfSetOfWords1: String, numberOfCardsLearned1: Int, numberOfCards1: Int,
    numOfNewCardsLeft1: Int, numOfCardsInSession1: Int
) {
    var nameOfSetOfWords: String = nameOfSetOfWords1
    var numberOfCardsLearned: Int = numberOfCardsLearned1
    var numberOfCardsInDeck: Int = numberOfCards1
    var numOfNewCardsLeft: Int = numOfNewCardsLeft1
    var numOfCardsInSession: Int = numOfCardsInSession1
}

class StudyObjectRepository(private val studyObjectDao: StudyObjectDao) {

    var decksData: MutableList<MyValues> = pom()

    fun updateDecksData(){
        decksData = pom()
    }

    fun getDeckValues(deckName: String): MyValues{
        val deckValues = MyValues("", 1, 1, 1, 1)
        deckValues.nameOfSetOfWords = deckName
        deckValues.numberOfCardsInDeck = studyObjectDao.getNumberOfWordsInDeck(deckName)
        deckValues.numberOfCardsLearned = studyObjectDao.getNumberOfWordsInDeckLearned(deckName)
        deckValues.numOfNewCardsLeft = prefs.getNumOfNewCardsLeft(deckName)
        val sessionNum: Int = prefs.getSessionNum(deckName)
        deckValues.numOfCardsInSession = studyObjectDao.getNumOfCardsInSession(sessionNum, deckName)
        return deckValues
    }

    private fun pom(): MutableList<MyValues> {
        val wordGroupNames: MutableList<String> = studyObjectDao.getDecksNames()
        val decksDataPom: MutableList<MyValues> = mutableListOf()
        wordGroupNames.forEach {
            val sth = MyValues("", 1, 1, 1, 1)
            sth.nameOfSetOfWords = it
            sth.numberOfCardsInDeck = studyObjectDao.getNumberOfWordsInDeck(it)
            sth.numberOfCardsLearned = studyObjectDao.getNumberOfWordsInDeckLearned(it)
            sth.numOfNewCardsLeft = prefs.getNumOfNewCardsLeft(it)
            val sessionNum: Int = prefs.getSessionNum(it)
            sth.numOfCardsInSession = studyObjectDao.getNumOfCardsInSession(sessionNum, it)
            decksDataPom.add(
                MyValues(
                    sth.nameOfSetOfWords, sth.numberOfCardsLearned,
                    sth.numberOfCardsInDeck, sth.numOfNewCardsLeft, sth.numOfCardsInSession
                )
            )
        }
        return decksDataPom
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(studyObject: StudyObject) {
        studyObjectDao.insert(studyObject)
    }
}