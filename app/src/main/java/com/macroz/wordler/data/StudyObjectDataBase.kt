package com.macroz.wordler.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StudyObject::class], version = 1)
abstract class StudyObjectDataBase: RoomDatabase() {
    abstract fun studyObjectDao(): StudyObjectDao
}