package com.macroz.wordler.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studyObject_table")
data class StudyObject (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val sessionNumber: Int,//-1 = not in session yet
    val wordGroupName: String,
    val mainWord: String,
    val mainWordDescription: String,
    val subsidiaryWord: String,
    val subsidiaryWordDescription: String,
    val numberOfCardsInDeck: Int
)