package com.ryannm.noteshero.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ryannm.noteshero.domain.NoteDao
import com.ryannm.noteshero.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun taskDao(): NoteDao
    companion object {
        private lateinit var instance: NoteDatabase

        fun init(applicationContext: Context) {
            instance = Room.databaseBuilder(
                applicationContext,
                NoteDatabase::class.java, "note-db"
            ).build()
        }

        fun getDao() = instance.taskDao()
    }
}
