package com.macroz.wordler.data

import androidx.annotation.WorkerThread
import com.macroz.wordler.prefs

class MyValues(
    nameOfSetOfWords1: String, numberOfCardsLearned1: Int, numberOfCards1: Int,
    numOfNewCardsLeft1: Int, numOfCardsInSession1: Int
) {
    var deckName: String = nameOfSetOfWords1
    var numberOfCardsLearned: Int = numberOfCardsLearned1
    var numberOfCardsInDeck: Int = numberOfCards1
    var numOfNewCardsLeft: Int = numOfNewCardsLeft1
    var numOfCardsInSession: Int = numOfCardsInSession1
}

class StudyObjectRepository(private val studyObjectDao: StudyObjectDao) {

    fun updateNumOfCardsInSession(deckName: String){
        val sessionNum = prefs.getSessionNum(deckName)
        val numOfNewCards: Int = prefs.getNumOfNewCards(deckName)
        val cardsLeft: Int = studyObjectDao.getNumOfCardsInSession(sessionNum, deckName)
        prefs.setNumOfCardsInSession(deckName, cardsLeft + numOfNewCards)
    }

    fun getNumOfCardsInSession(deckName: String): Int {
        val sessionNum = prefs.getSessionNum(deckName)
        val numOfNewCardsLeft: Int = prefs.getNumOfNewCardsLeft(deckName)
        val cardsLeft: Int = studyObjectDao.getNumOfCardsInSession(sessionNum, deckName)
        return cardsLeft + numOfNewCardsLeft
    }

    fun getSession(sessionNum: Int, deckName: String): MutableList<StudyObject> {
        return studyObjectDao.getSession(sessionNum, deckName)
    }

    fun getSession(sessionNum: Int, deckName: String, numOfCardsToGet: Int): MutableList<StudyObject> {
        return studyObjectDao.getSession(sessionNum, deckName, numOfCardsToGet)
    }

    fun getNumOfCardsInSession(sessionNum: Int, deckName: String): Int {
        return studyObjectDao.getNumOfCardsInSession(sessionNum, deckName)
    }

    fun recoverNumOfCardsInSession(deckName: String): Int {
        return prefs.getNumOfCardsInSession(deckName)
    }

    fun getNumOfCardsLearnedInSession(deckName: String): Int {
        val sessionNum = prefs.getSessionNum(deckName)
        val numOfNewCardsLeft: Int = prefs.getNumOfNewCardsLeft(deckName)
        val cardsLeft: Int = studyObjectDao.getNumOfCardsInSession(sessionNum, deckName)
        return recoverNumOfCardsInSession(deckName) - numOfNewCardsLeft - cardsLeft
    }

    fun getDeckValues(deckName: String): MyValues{
        val deckValues = MyValues("", 1, 1, 1, 1)
        deckValues.deckName = deckName
        deckValues.numberOfCardsInDeck = studyObjectDao.getNumberOfWordsInDeck(deckName)
        deckValues.numberOfCardsLearned = studyObjectDao.getNumberOfWordsInDeckLearned(deckName)
        deckValues.numOfNewCardsLeft = prefs.getNumOfNewCardsLeft(deckName)
        val sessionNum: Int = prefs.getSessionNum(deckName)
        deckValues.numOfCardsInSession = studyObjectDao.getNumOfCardsInSession(sessionNum, deckName)
        return deckValues
    }

    fun resetDeck(deckName: String){
        studyObjectDao.resetDeck(deckName)
    }

    fun getAllCardsInDeck(deckName: String): List<StudyObject>{
        return studyObjectDao.getAllCardsInDeck(deckName)
    }

    fun getStudyObject(Id: Int): StudyObject {
        return studyObjectDao.getStudyObject(Id)
    }

    fun getDeckNames(): MutableList<String> {
        return studyObjectDao.getDecksNames()
    }

    fun getNumOfCardsInDeck(deckName: String) :Int {
        return studyObjectDao.getNumberOfWordsInDeck(deckName)
    }

    fun getNumOfCardsInDeckLearned(deckName: String) : Int {
        return studyObjectDao.getNumberOfWordsInDeckLearned(deckName)
    }

    fun randomizeOrderId(deckName: String) {
        studyObjectDao.randomizeOrderId(deckName)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(studyObject: StudyObject) {
        studyObjectDao.insert(studyObject)
    }

    suspend fun insertAndReplace(studyObject: StudyObject) {
        studyObjectDao.insertAndReplace(studyObject)
    }
}