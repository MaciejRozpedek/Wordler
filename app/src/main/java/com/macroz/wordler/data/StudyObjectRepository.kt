package com.macroz.wordler.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class myValues(
    val nameOfSetOfWords: String,
    val numberOfStudyObjectsLearned: Int,
    val numberOfStudyObjects: Int
    //something about current session
)

class StudyObjectRepository(private val studyObjectDao: StudyObjectDao) {

    val wordGroupNames: Flow<List<StudyObject>> = studyObjectDao.getWordGroupNames()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(studyObject: StudyObject) {
        studyObjectDao.insert(studyObject)
    }
}