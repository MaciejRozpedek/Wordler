package com.macroz.wordler.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class myValues(nameOfSetOfWords1: String, numberOfCardsLearned1: Int, numberOfCards1: Int) {
    var nameOfSetOfWords: String = nameOfSetOfWords1
    var numberOfCardsLearned: Int = numberOfCardsLearned1
    var numberOfCards: Int = numberOfCards1
    //something about current session
}

class StudyObjectRepository(private val studyObjectDao: StudyObjectDao) {

    var decksData: MutableList<myValues> = pom()

    private fun pom(): MutableList<myValues> {
        var wordGroupNames: MutableList<String> = studyObjectDao.getDecksNames()
        var decksDatapom: MutableList<myValues> = mutableListOf()
        wordGroupNames.forEach {
            var sth: myValues = myValues("", 1, 1)
            sth.nameOfSetOfWords = it
            sth.numberOfCards = studyObjectDao.getNumberOfWordsInDeck(it)
            sth.numberOfCardsLearned = studyObjectDao.getNumberOfWordsInDeckLearned(it)
            decksDatapom.add(myValues(sth.nameOfSetOfWords, sth.numberOfCardsLearned, sth.numberOfCards))
        }
        return decksDatapom
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(studyObject: StudyObject) {
        studyObjectDao.insert(studyObject)
    }
}