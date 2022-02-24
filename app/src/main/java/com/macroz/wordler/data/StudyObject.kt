package com.macroz.wordler.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studyObject_table")
data class StudyObject (
    @PrimaryKey(autoGenerate = false)
    val sessionNumber: Int,
    val wordGroupName: String,
    val mainWord: String,
    val mainWordDescription: String,
    val subsidiaryWord: String,
    val subsidiaryWordDescription: String
)