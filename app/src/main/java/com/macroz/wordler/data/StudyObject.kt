package com.macroz.wordler.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studyObject_table")
data class StudyObject (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val orderId: Int,   //TODO("to set when creating deck")
    var sessionNumber: Int,//-1 = not in session yet
    val wordGroupName: String,
    val mainWord: String,
    val mainWordDescription: String,
    val answerWord: String,
    val answerWordDescription: String,
    var lastWaitingTime: Int    // -1 = "new card", -2 = "review card"
)