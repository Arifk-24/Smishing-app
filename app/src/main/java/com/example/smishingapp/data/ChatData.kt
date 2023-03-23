package com.example.smishingapp.data

import com.example.smishingapp.ui.chat.MessageChat
import java.sql.Time

val chat = (0..100).map {
    MessageChat(
        "Dear patron Plz ignore the earlier SMS on due date and amt for may, we will send out the corrected SMS in a while. Regret the inconvenience caused.  Team ACT",
        time = Time(234444),
        "arif",
        "inbox"
    )
}.toList()