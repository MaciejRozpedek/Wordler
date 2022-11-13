package com.macroz.wordler

import android.app.Application
import com.macroz.wordler.data.Prefs
import com.macroz.wordler.data.StudyObjectDataBase
import com.macroz.wordler.data.StudyObjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

val prefs: Prefs by lazy {
    WordlerApplication.prefs!!
}

val utensils: Utensils by lazy {
    WordlerApplication.utensils!!
}

class WordlerApplication: Application() {

    companion object {
        var utensils: Utensils? = null
        var prefs: Prefs? = null
        lateinit var instance: WordlerApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        utensils = Utensils(applicationContext)
        prefs = Prefs(applicationContext)
    }

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { StudyObjectDataBase.getDatabase(this, applicationScope) }
    val repository by lazy { StudyObjectRepository(database.studyObjectDao()) }
}