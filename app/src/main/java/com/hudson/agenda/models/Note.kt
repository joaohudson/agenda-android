package com.hudson.agenda.models

import java.io.Serializable

data class Note(
    val title: String,
    val content: String
) : Serializable
