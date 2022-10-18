package com.macroz.wordler.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [StudyObject::class], version = 1)
abstract class StudyObjectDataBase : RoomDatabase() {

    abstract fun studyObjectDao(): StudyObjectDao

    companion object {

        @Volatile
        private var INSTANCE: StudyObjectDataBase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): StudyObjectDataBase {
            return INSTANCE ?: synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyObjectDataBase::class.java,
                    "studyObject_database"
                )
                    .addCallback(StudyObjectDatabaseCallback(scope))
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = tempInstance
                // return instance
                tempInstance
            }
        }

        private class StudyObjectDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.studyObjectDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(studyObjectDao: StudyObjectDao) {

            studyObjectDao.deleteAll()

            var studyObject = StudyObject(
                0, -1, "ACT vocabulary",
                "m", "md", "s", "sd", -1
            )
            repeat(50) {
                studyObjectDao.insert(
                    StudyObject(
                        it+2345,
                        -1,
                        "Studying Test",
                        "main${it + 1}",
                        "mainDesc${it + 1}",
                        "subs${it + 1}",
                        "subsDesc${it + 1}",
                        -1
                    )
                )
            }
            repeat(10) {
                studyObjectDao.insert(
                    StudyObject(
                        it + 1, -1, "ACT vocabulary",
                        "m", "md", "s", "sd", -1
                    )
                )
            }
            repeat(15) {
                studyObjectDao.insert(
                    StudyObject(
                        it + 1001, -1, "ACT vocabulary",
                        "m", "md", "s", "sd", -1
                    )
                )
            }
            studyObject = StudyObject(
                0, -1, "English C1",
                "main1", "mainD1", "subs1",
                "subsD1", -1
            )
            studyObjectDao.insert(studyObject)
            studyObject = StudyObject(
                0, -1, "English C1",
                "main2", "mainD2", "subs2",
                "subsD2", -1
            )
            studyObjectDao.insert(studyObject)
            studyObject = StudyObject(
                0, -1, "English C1",
                "main3", "mainD3", "subs3",
                "subsD3", -1
            )
            studyObjectDao.insert(studyObject)
        }
    }
}