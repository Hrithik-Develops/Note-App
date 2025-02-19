package com.singh.noteapp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.singh.noteapp.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Note::class],version = 1)
 abstract class NoteDatabase:RoomDatabase() {

     abstract fun getNoteDao():NoteDAO

     companion object{

         @Volatile
         private var INSTANCE: NoteDatabase?=null

         fun getDatabase(context: Context, scope: CoroutineScope): NoteDatabase{
            return INSTANCE?: synchronized(this){
                 val instance = Room.databaseBuilder(
                     context.applicationContext,
                     NoteDatabase::class.java,
                     "note_database")
                     .addCallback(NoteDatabaseCallback(scope))
                     .build()

                INSTANCE = instance
                instance
            }
         }
     }

    private class NoteDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database->
                scope.launch {

                    val noteDao = database.getNoteDao()
                    noteDao.insert(Note("Title1","Description1"))
                    noteDao.insert(Note("Title2","Description2"))
                    noteDao.insert(Note("Title3","Description3"))

                }

            }
        }
    }
}