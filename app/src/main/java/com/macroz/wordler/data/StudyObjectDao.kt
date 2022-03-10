package com.macroz.wordler.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudyObjectDao {

    //TODO If word that user provided already exist app should ask user what to do
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addStudyObject(studyObject: StudyObject)

    @Query("SELECT * " +
            "FROM studyObject_table " +
            "WHERE sessionNumber==:session_number AND wordGroupName==:word_group_name")
    fun getSession(session_number: Int, word_group_name: String): List<StudyObject>

    //return all wordGroupNames
    @Query("SELECT DISTINCT wordGroupName FROM studyObject_table")
    fun getWordGroupNames(): List<String>
}