package com.ryannm.tasky

import android.app.Application
import com.ryannm.noteshero.data.NoteDatabase

class App: Application() {
    @Override
    override fun onCreate() {
        super.onCreate()

        NoteDatabase.init(this)
    }
}