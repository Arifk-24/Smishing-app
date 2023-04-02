package com.example.smishingapp.ui

data class MessageUiState(
    val allMessage: MutableList<Message> = mutableListOf(),
    val spam: MutableList<Message> = mutableListOf(),
    val ham: MutableList<Message> = mutableListOf(),
    val chat: MutableList<Message> = mutableListOf()
    )