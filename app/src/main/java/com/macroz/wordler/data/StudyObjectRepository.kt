package com.macroz.wordler.data

import androidx.annotation.WorkerThread
import com.macroz.wordler.prefs

class MyValues(
    nameOfSetOfWords1: String, numberOfCardsLearned1: Int, numberOfCards1: Int,
    numOfNewCardsLeft1: Int, numOfCardsInSession1: Int
) {
    var nameOfSetOfWords: String = nameOfSetOfWords1
    var numberOfCardsLearned: Int = numberOfCardsLearned1
    var numberOfCards: Int = numberOfCards1
    var numOfNewCardsLeft: Int = numOfNewCardsLeft1
    var numOfCardsInSession: Int = numOfCardsInSession1
}

class StudyObjectRepository(private val studyObjectDao: StudyObjectDao) {

    var decksData: MutableList<MyValues> = pom()

    private fun pom(): MutableList<MyValues> {
        val wordGroupNames: MutableList<String> = studyObjectDao.getDecksNames()
        val decksDataPom: MutableList<MyValues> = mutableListOf()
        wordGroupNames.forEach {
            val sth = MyValues("", 1, 1, 1, 1)
            sth.nameOfSetOfWords = it
            sth.numberOfCards = studyObjectDao.getNumberOfWordsInDeck(it)
            sth.numberOfCardsLearned = studyObjectDao.getNumberOfWordsInDeckLearned(it)
            sth.numOfNewCardsLeft = prefs.getNumOfNewCardsLeft(it)
            val sessionNum: Int = prefs.getSessionNum(it)
            sth.numOfCardsInSession = studyObjectDao.getNumOfCardsInSession(sessionNum, it)
            decksDataPom.add(
                MyValues(
                    sth.nameOfSetOfWords, sth.numberOfCardsLearned,
                    sth.numberOfCards, sth.numOfNewCardsLeft, sth.numOfCardsInSession
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