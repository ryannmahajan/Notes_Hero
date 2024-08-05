package com.ryannm.tasky

import android.app.Application
import com.ryannm.noteshero.data.NoteDatabase
import com.ryannm.noteshero.domain.SharedPref

class App: Application() {
    @Override
    override fun onCreate() {
        super.onCreate()

        NoteDatabase.init(this)
        SharedPref.init(this)
    }
}