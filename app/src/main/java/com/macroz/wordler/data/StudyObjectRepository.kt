package com.macroz.wordler.data

import androidx.annotation.WorkerThread

class StudyObjectRepository(private val studyObjectDao: StudyObjectDao) {

    val wordGroupNames: List<StudyObject> = studyObjectDao.getWordGroupNames()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(studyObject: StudyObject) {
        studyObjectDao.addStudyObject(studyObject)
    }
}