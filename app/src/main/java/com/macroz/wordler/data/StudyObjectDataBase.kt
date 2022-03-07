package com.macroz.wordler.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudyObject::class], version = 1)
abstract class StudyObjectDataBase: RoomDatabase() {
    abstract fun studyObjectDao(): StudyObjectDao

    companion object {

        @Volatile
        private var INSTANCE: StudyObjectDataBase? = null

        fun getDatabase(context: Context): StudyObjectDataBase {
            return INSTANCE ?: synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyObjectDataBase::class.java,
                    "studyObject_database"
                ).build()
                INSTANCE = tempInstance
                // return instance
                tempInstance
            }
        }
    }
}