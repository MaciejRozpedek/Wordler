package com.macroz.wordler

import android.app.Application
import com.macroz.wordler.data.StudyObjectDataBase
import com.macroz.wordler.data.StudyObjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordlerApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { StudyObjectDataBase.getDatabase(this, applicationScope) }
    val repository by lazy { StudyObjectRepository(database.studyObjectDao()) }
}