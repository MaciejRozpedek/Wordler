package com.macroz.wordler.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudyObjectDao {

    //TODO If word that user provided already exist app should ask user what to do
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStudyObject(studyObject: StudyObject)

    //TODO pass param to @Querry
    @Query("SELECT * FROM studyObject_table /*WHERE sessionNumber==param*/")
    fun getSession(param: Int): List<StudyObject>
}