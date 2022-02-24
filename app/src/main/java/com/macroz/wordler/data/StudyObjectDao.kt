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

    @Query("SELECT * " +
            "FROM studyObject_table " +
            "WHERE sessionNumber==:param AND wordGroupName==:word_group_name")
    fun getSession(param: Int, word_group_name: String): List<StudyObject>
}