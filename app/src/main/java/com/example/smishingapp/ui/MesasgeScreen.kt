package com.example.smishingapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smishingapp.data.message_list
import com.example.smishingapp.ui.theme.SmishingAppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MessageScreen(modifier: Modifier = Modifier){
    Scaffold(
        topBar = {MessageTopAppBar(modifier)}
    ){
        MessageList(modifier)
    }
}

@Composable
fun MessageTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(text = "Messages")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 10.dp
    )
}

@Preview
@Composable
fun MessageScreenPreview(){
    SmishingAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MessageScreen(Modifier)
        }
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier) {
        LazyColumn() {
            message_list.forEach { (sender, message) ->
                items(1) {
                    MessageView(message = message)
                }
            }
    }
}

@Composable
fun MessageView(message: Message){
    Card(modifier = Modifier.padding(4.dp), elevation = 4.dp) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(message.sender, fontSize = 20.sp)
                Text(message.text, fontSize = 10.sp)
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(message.type, fontSize = 10.sp)
            }
        }
    }
}
