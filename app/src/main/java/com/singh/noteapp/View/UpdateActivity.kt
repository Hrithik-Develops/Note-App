package com.singh.noteapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.singh.noteapp.R

class UpdateActivity : AppCompatActivity() {
    lateinit var noteTitleEdtUpdt: EditText
    lateinit var noteDescEdtUpdt: EditText
    lateinit var cancelBtnUpdt: Button
    lateinit var saveBtnUpdt: Button
    var currentId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        noteDescEdtUpdt=findViewById(R.id.editTextDescriptionUpdate)
        noteTitleEdtUpdt=findViewById(R.id.editTextTitleUpdate)
        cancelBtnUpdt=findViewById(R.id.buttonCancelUpdate)
        saveBtnUpdt=findViewById(R.id.buttonSaveUpdate)

         getAndSetData()

        cancelBtnUpdt.setOnClickListener {
            finish()
        }

        saveBtnUpdt.setOnClickListener{

            updateNote()

        }
    }

    fun updateNote() {

        val updatedTitle = noteTitleEdtUpdt.text.toString()
        val updatedDesc = noteDescEdtUpdt.text.toString()

        val intent = Intent()
        intent.putExtra("updatedTitle", updatedTitle)
        intent.putExtra("updatedDescription", updatedDesc)
        if (currentId != -1) {
            intent.putExtra("noteId", currentId)
            setResult(RESULT_OK, intent)
            finish()


        }
    }
    fun getAndSetData(){
        val currentTitle= intent.getStringExtra("currentTitle")
        val currentDescription= intent.getStringExtra("currentDescription")
        currentId= intent.getIntExtra("currentId",-1)
        noteTitleEdtUpdt.setText(currentTitle)
        noteDescEdtUpdt.setText(currentDescription)


    }
}