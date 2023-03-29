package com.example.smishingapp.ui

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.Telephony
import androidx.lifecycle.ViewModel

class MessageModel:ViewModel() {

    @SuppressLint("Recycle")
    fun getAllMessage(context:Context): MutableList<Message>{

        val contentResolver: ContentResolver = context.contentResolver
        val projection = arrayOf(Telephony.Sms.Conversations.SNIPPET)
        val cursor = contentResolver.query(
            Telephony.Sms.Conversations.CONTENT_URI,
            projection, null, null, null
        )
        val messageList = mutableListOf<Message>()
        if (cursor != null) {
            while(cursor.moveToNext()) {
                val message = Message(
                    text = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Conversations.SNIPPET)),
                    sender = "Arif",
                    type = "inbox"
                    )
                messageList.add(message)
            }
        }
        return messageList
    }
}

