package com.singh.noteapp.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.singh.noteapp.Model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDAO {

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("Select * from note_table order by id ASC")
    fun getAllNotes(): Flow<List<Note>>

}