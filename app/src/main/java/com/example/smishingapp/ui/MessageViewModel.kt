package com.example.smishingapp.ui

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.Telephony
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


fun addElement(myHashMap:HashMap<String,MutableList<Message> >, myKey:String , newMessage: Message){
    if (myHashMap.containsKey(myKey)) {

        val myList = myHashMap[myKey]
        myList?.add(newMessage)
        myHashMap[myKey] = myList!!
    } else {
        // If the key doesn't exist, create a new MutableList and add the new element
        val myList = mutableListOf(newMessage)
        myHashMap[myKey] = myList
    }
}

class MessageViewModel:ViewModel() {

    private val _uiState = MutableStateFlow(MessageUiState())
    val uiState: StateFlow<MessageUiState> = _uiState.asStateFlow()

    private val map: HashMap<String, MutableList<Message> > = hashMapOf()

    private val dateComparator = Comparator<Message> { m1, m2 ->
        m2.date.compareTo(m1.date)
    }

    @SuppressLint("Recycle")
    fun getAllMessage(context: Context): MutableList<Message> {

        val contentResolver: ContentResolver = context.contentResolver
        val projection = arrayOf(
            Telephony.Sms.BODY,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.TYPE,
            Telephony.Sms.DATE
        )
        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection, null, null, null
        )
        val messageList = mutableListOf<Message>()

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val date = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                val address:String? = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val type = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                val message = Message(
                    text = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)),
                    sender = address ?: "",
                    date = date,
                    type = when(type){
                        Telephony.Sms.MESSAGE_TYPE_INBOX -> "inbox"
                        else -> "outbox"
                    }
                )
                addElement(map,address?:"",message)
            }
        }
        map.forEach{
            messageList.add(it.value.first())
        }
        messageList.sortWith(dateComparator)
        return messageList
    }


    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("MM/dd, yy", Locale.getDefault())
        return format.format(date)
    }

    fun updateMessage(messageList: MutableList<Message>) {
        _uiState.update { currentState ->
            currentState.copy(
                allMessage = messageList
            )
        }
    }

    fun updateConversation(address: String){
        val messageList = map[address]!!
        _uiState.update{currentState ->
            currentState.copy(
                chat = messageList
            )
        }
    }
}

