package com.singh.noteapp.View

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.singh.noteapp.Adapters.NoteAdapter
import com.singh.noteapp.Model.Note
import com.singh.noteapp.NoteApplication
import com.singh.noteapp.R
import com.singh.noteapp.ViewModel.NoteViewModel
import com.singh.noteapp.ViewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var toolbar: MaterialToolbar
    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(this)
        recyclerView.adapter = noteAdapter

        registerActivityResultLauncher()


        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)

        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.viewAllNotes.observe(this, Observer { notes ->


            noteAdapter.setNote(notes)


        })

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)



        toolbar.inflateMenu(R.menu.new_menu)



        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_add_note -> {
                    val intent = Intent(this, NoteAddActivity::class.java)
                    addActivityResultLauncher.launch(intent)

                }

                R.id.item_delete_note -> {showDialogMessage()}

            }

            return@setOnMenuItemClickListener true
        }
    }

    fun registerActivityResultLauncher() {

        addActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { resultAddNote ->
                    val resultCode = resultAddNote.resultCode
                    val data = resultAddNote.data
                    if (resultCode == RESULT_OK && data != null) {
                        val titleOfNote: String = data.getStringExtra("title").toString()
                        val descOfNote: String = data.getStringExtra("description").toString()
                        val note = Note(titleOfNote, descOfNote)
                        noteViewModel.insert(note)

                    }
                })
        updateActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { resultUpdateNote ->
                    val resultCode = resultUpdateNote.resultCode
                    val data = resultUpdateNote.data
                    if (resultCode == RESULT_OK && data != null) {

                        val updatedTitleOfNote:String= data.getStringExtra("updatedTitle").toString()
                        val updatedDescOfNote:String= data.getStringExtra("updatedDescription").toString()
                        val noteId= data.getIntExtra("noteId",-1)

                        val newNote= Note(updatedTitleOfNote,updatedDescOfNote)
                        newNote.id= noteId
                        noteViewModel.update(newNote)



                    }
                })

    }

    fun showDialogMessage() {
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Notes")
        dialogMessage.setMessage("If click yes all notes will delete, if you want to delete a particular note swipe left or right, if you want to continue then click no")
        dialogMessage.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        dialogMessage.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            noteViewModel.deleteAllNotes()




        })

        dialogMessage.create().show()


    }
}



