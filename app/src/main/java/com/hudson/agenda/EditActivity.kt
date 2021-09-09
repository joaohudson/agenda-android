package com.hudson.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.hudson.agenda.models.IntentKeys
import com.hudson.agenda.models.Note
import com.hudson.agenda.models.NoteRepository

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val noteIndex = intent.getIntExtra(IntentKeys.NoteIndex, -1)

        val noteTitle = findViewById<EditText>(R.id.noteTitle)
        val noteContent = findViewById<EditText>(R.id.noteContent)

        val note = NoteRepository.notes[noteIndex]

        noteTitle.setText(note.title)
        noteContent.setText(note.content)

        val saveBtn = findViewById<Button>(R.id.noteSave)
        saveBtn.setOnClickListener{
            save(noteIndex, noteTitle, noteContent)
        }

        val cancelBtn = findViewById<Button>(R.id.noteCancel)
        cancelBtn.setOnClickListener{
            cancel()
        }
    }

    fun save(noteIndex: Int, noteTitle: EditText, noteContent: EditText){

        val title = noteTitle.text.toString()
        val content = noteContent.text.toString()

        NoteRepository.notes.set(noteIndex, Note(title, content))

        val msg = Intent(this, MainActivity::class.java)
        startActivity(msg)
    }

    fun cancel(){
        finish()
    }
}