package com.singh.noteapp

import android.app.Application
import com.singh.noteapp.Repository.NoteRepository
import com.singh.noteapp.Room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { NoteDatabase.getDatabase(this, applicationScope) }
    val repository by lazy{ NoteRepository(database.getNoteDao()) }



}