package com.example.smishingapp.ui.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.smishingapp.data.chat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatScreen(modifier: Modifier = Modifier, conversation: List<MessageChat>,navController: NavHostController){
    Scaffold(
        modifier.fillMaxSize(),
        topBar = {ChatTopBar(navController = navController)}
    ){
        ChatScreenView(conversation)
    }
}

@Preview
@Composable
fun PreviewChatScreen(){
    ChatScreen(Modifier, conversation = chat, navController = rememberNavController())
}
@Composable
fun ChatTopBar(modifier:Modifier = Modifier,navController: NavHostController){
    TopAppBar(
        title = {
                Text(text = "Messages", modifier = modifier.padding(start = 5.dp))
        },
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(Icons.Filled.ArrowBack, "Back Arrow")
            }
        },
        actions= {
            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Message Icon")
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 10.dp
    )
}

@Composable
fun ChatScreenView(conversation: List<MessageChat>) {
    LazyColumn {
        conversation.forEach {message->
            items(1) {
                if(message.type == "inbox")
                ChatView(message)
                else
                    ChatViewLeft(message)
            }
        }
    }
}
@Composable
fun ChatViewLeft(messageChat: MessageChat)
{
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
        , modifier = Modifier
            .padding(start = 80.dp,top = 15.dp)
    ) {
        Text(messageChat.time.toString(),
            modifier = Modifier)
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(15.dp),
            backgroundColor = MaterialTheme.colors.secondary,
        ){
            Text(messageChat.chat,
                Modifier.padding(10.dp,10.dp),textAlign = TextAlign.Justify)
        }
    }
}
@Composable
fun ChatView(messageChat: MessageChat) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
        , modifier = Modifier
            .padding(top = 15.dp,end = 100.dp)
    ) {
        Text(messageChat.time.toString(),
            modifier = Modifier)
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(15.dp),
            backgroundColor = MaterialTheme.colors.secondary,
        ){
            Text(messageChat.chat,
                Modifier.padding(10.dp,10.dp),textAlign = TextAlign.Justify)
        }
    }
}