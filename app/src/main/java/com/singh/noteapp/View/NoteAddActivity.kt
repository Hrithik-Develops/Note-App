package com.singh.noteapp.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.singh.noteapp.R

class NoteAddActivity : AppCompatActivity() {

    lateinit var noteTitleEdt: EditText
    lateinit var noteDescEdt: EditText
    lateinit var cancelBtn: Button
    lateinit var saveBtn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        noteTitleEdt=findViewById(R.id.editTextTitle)
        noteDescEdt=findViewById(R.id.editTextDescription)
        cancelBtn=findViewById(R.id.buttonCancel)
        saveBtn=findViewById(R.id.buttonSave)

        cancelBtn.setOnClickListener {
            finish()
        }

        saveBtn.setOnClickListener{


            saveNote()

        }




    }

    fun saveNote(){

        val titleOfNote: String=noteTitleEdt.text.toString()
        val descOfNote: String=noteDescEdt.text.toString()
        val intent=Intent()
        intent.putExtra("title",titleOfNote)
        intent.putExtra("description",descOfNote)
        setResult(RESULT_OK,intent)
        finish()


    }



}