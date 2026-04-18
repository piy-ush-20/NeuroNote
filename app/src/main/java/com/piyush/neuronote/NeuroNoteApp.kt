package com.piyush.neuronote;

import android.app.Application
import timber.log.Timber
import com.piyush.neuronote.core.util.Logger
import com.piyush.neuronote.data.local.NoteDatabase

class NeuroNoteApp : Application() {

    lateinit var database: NoteDatabase
        private set


    override fun onCreate() {
        super.onCreate()

        // Timber setup
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Database initialization
        database = NoteDatabase.getInstance(this)
        Logger.d("NeuroNoteApp", "App created")
    }
}