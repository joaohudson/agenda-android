package com.hudson.agenda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hudson.agenda.models.Note

class NoteAdapter(val notes : ArrayList<Note>, val context : Context) : BaseAdapter() {
    override fun getCount(): Int {
        return notes.size
    }

    override fun getItem(i: Int): Any {
        return notes[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view : View?, parent : ViewGroup?): View {

        val resultView = LayoutInflater.from(context).inflate(R.layout.note_view, parent, false) as TextView

        val note = notes[i]
        resultView.text = note.title

        return resultView
    }
}