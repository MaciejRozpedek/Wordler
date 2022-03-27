package com.macroz.wordler.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyObjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(studyObject: StudyObject)

    @Query("SELECT * " +
            "FROM studyObject_table " +
            "WHERE sessionNumber==:session_number AND wordGroupName==:word_group_name")
    fun getSession(session_number: Int, word_group_name: String): List<StudyObject>

    //return all wordGroupNames
//    @Query("SELECT DISTINCT wordGroupName FROM studyObject_table")
    @Query("SELECT * FROM studyObject_table")
    fun getWordGroupNames(): Flow<List<StudyObject>>

    //return number of words in word group
    @Query("SELECT COUNT(*) " +
            "FROM studyObject_table " +
            "WHERE wordGroupName==:word_group_name")
    fun getNumberOfWordsInSet(word_group_name: String): Int

    @Query("SELECT COUNT(*) " +
            "FROM studyObject_table " +
            "WHERE wordGroupName==:word_group_name AND sessionNumber!=-1")
    fun getNumberOfWordsInSetInUse(word_group_name: String):Int

    @Query("SELECT COUNT(*) " +
            "FROM studyObject_table " +
            "WHERE wordGroupName==:word_group_name AND sessionNumber==-1")
    fun getNumberOfWordsInSetNotInUse(word_group_name: String): Int

    @Query("DELETE FROM studyObject_table")
    suspend fun deleteAll()
}