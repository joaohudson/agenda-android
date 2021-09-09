package com.hudson.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hudson.agenda.models.IntentKeys
import com.hudson.agenda.models.Note
import com.hudson.agenda.models.NoteRepository
import java.io.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        val list = findViewById<ListView>(R.id.list)
        //manda um intent para a EditView com o índice da nota a ser editada
        list.setOnItemClickListener{ _ , _ , i , _ ->
            val msg = Intent(this, EditActivity::class.java)
            msg.putExtra(IntentKeys.NoteIndex, i)
            startActivity(msg)
        }

        val adapter = NoteAdapter(NoteRepository.notes, this)
        list.adapter = adapter

        val btn = findViewById<FloatingActionButton>(R.id.addNoteButton)
        btn.setOnClickListener{
            NoteRepository.notes.add(Note(getString(R.string.newNote), ""))
            adapter.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        writeData(NoteRepository.notes)
        super.onStop()
    }

    fun loadData(){
        try{
            if(!NoteRepository.notes.isEmpty())
                return

            val file = File(filesDir, "notes.dat")

            if(!file.exists())
                return

            val bytes = file.readBytes()
            val bs = ByteArrayInputStream(bytes)
            val os = ObjectInputStream(bs)
            val notes = os.readObject() as ArrayList<Note>
            NoteRepository.notes.addAll(notes)

            notes.forEach{ n ->
                Log.d("Agenda", n.title + " - " + n.content)
            }

            os.close()
            bs.close()
        }
        catch (e : Exception){
            Log.d("Agenda", e.toString())
        }
    }

    fun writeData(obj : ArrayList<Note>){
        try {
            val file = File(filesDir, "notes.dat")
            val bs = ByteArrayOutputStream()

            Log.d("Agenda", file.absolutePath)

            val os = ObjectOutputStream(bs)
            os.writeObject(obj)

            val bytes = bs.toByteArray()
            file.writeBytes(bytes)

            os.close()
            bs.close()
        }catch (e : Exception){
            Log.d("Agenda", e.toString())
        }
    }
}