package com.macroz.wordler.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudyObjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(studyObject: StudyObject)

    @Query("SELECT *" +
            "FROM studyObject_table " +
            "WHERE wordGroupName==:deckName")
    fun getAllCardsInDeck(deckName: String): List<StudyObject>

    @Query("SELECT *" +
            "FROM studyObject_table " +
            "WHERE sessionNumber==:session_number AND wordGroupName==:deckName " +
            "ORDER BY RANDOM() " +
            "LIMIT 1")
    fun getStudyObject(session_number: Int, deckName: String): StudyObject

    @Query("SELECT COUNT(*)" +
            "FROM studyObject_table " +
            "WHERE sessionNumber==:session_number AND wordGroupName==:deckName")
    fun getNumOfStudyObjectsInSession(session_number: Int, deckName: String): Int

    @Query("UPDATE studyObject_table " +
            "SET sessionNumber = -1 " +
            "WHERE wordGroupName==:deckName")
    fun resetDeck(deckName: String)

    @Query("SELECT * " +
            "FROM studyObject_table " +
            "WHERE sessionNumber==:session_number AND wordGroupName==:word_group_name")
    fun getSession(session_number: Int, word_group_name: String): List<StudyObject>

    @Query("SELECT COUNT(*) " +
            "FROM studyObject_table " +
            "WHERE sessionNumber==:session_number AND wordGroupName==:word_group_name")
    fun getNumOfCardsInSession(session_number: Int, word_group_name: String): Int

    //return all wordGroupNames
    @Query("SELECT wordGroupName" +
            " FROM studyObject_table" +
            " GROUP BY wordGroupName")
    fun getDecksNames(): MutableList<String>

    //return number of words in word group
    @Query("SELECT COUNT(*) " +
            "FROM studyObject_table " +
            "WHERE wordGroupName==:word_group_name")
    fun getNumberOfWordsInDeck(word_group_name: String): Int

    @Query("SELECT COUNT(*) " +
            "FROM studyObject_table " +
            "WHERE wordGroupName==:word_group_name AND sessionNumber!=-1")
    fun getNumberOfWordsInDeckLearned(word_group_name: String):Int



    @Query("SELECT COUNT(*) " +
            "FROM studyObject_table " +
            "WHERE wordGroupName==:word_group_name AND sessionNumber==-1")
    fun getNumberOfWordsInDeckNotInUse(word_group_name: String): Int

    @Query("DELETE FROM studyObject_table")
    suspend fun deleteAll()
}