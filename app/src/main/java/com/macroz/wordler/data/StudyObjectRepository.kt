package com.macroz.wordler.data

import androidx.annotation.WorkerThread

class StudyObjectRepository(private val studyObjectDao: StudyObjectDao) {

    val wordGroupNames: List<String> = studyObjectDao.getWordGroupNames()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(studyObject: StudyObject) {
        studyObjectDao.addStudyObject(studyObject)
    }
}