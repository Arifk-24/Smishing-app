package com.example.smishingapp.ui.chat

import java.sql.Time

//MessageChat class stores the data of the chat message
//chat: A string value that store the message
//time: Time at which the message was sent or received
//address: The Address of the other party involved in the conversation
//type: The type of message inbox or outbox
data class MessageChat (val chat: String, val time: Time,val address: String,val type:String)