package com.example.smishingapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smishingapp.data.chat
import com.example.smishingapp.data.message_list
import com.example.smishingapp.ui.chat.ChatScreen
import com.example.smishingapp.ui.theme.SmishingAppTheme
import kotlinx.coroutines.launch


@Composable
fun MyAppNavHost(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination:String = "All Messages"
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("All Messages"){
            MessageScreen(modifier,navController, title = "All Messages")
        }
        composable("Spam"){
            MessageScreen(modifier,navController, title = "Spam")
        }
        composable("Ham"){
            MessageScreen(modifier,navController, title = "Ham")
        }
        composable("Chat"){
            ChatScreen(conversation = chat, navController = navController)
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MessageScreen(modifier: Modifier = Modifier,
                  navController: NavHostController,
                  title: String
){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 5.dp)
            ){
                Card(modifier = Modifier
                    .fillMaxWidth()
                ){
                    Button(onClick = {navController.navigate("All Messages")}
                    ) {
                        Text("Home")
                    }
                }
                Card(modifier = Modifier.fillMaxWidth()){
                    Button(onClick = {navController.navigate("Spam")}) {
                        Text("Spam")
                    }
                }
                Card(modifier = Modifier.fillMaxWidth()){
                    Button(onClick = {navController.navigate("Ham")}) {
                        Text("Ham")
                    }
                }
            }
        },
        topBar = {
            MessageTopAppBar(modifier, title) {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        }
    ){
        MessageList(navController)
    }
}


@Composable
fun MessageTopAppBar(modifier: Modifier = Modifier,
                     title: String,
                     onMenuClick:()->Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Email, contentDescription = "Message Icon")
                Text(text = title, modifier = modifier.padding(start = 5.dp))
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Filled.Menu, "Menu Button")
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
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MyAppNavHost(modifier = Modifier)
        }
    }
}

@Composable
fun MessageList(navController: NavHostController) {
        LazyColumn{
            message_list.forEach { (sender, message) ->
                items(1) {
                    MessageView(message = message,navController)
                }
            }
    }
}

@Composable
fun MessageView(message: Message,navController: NavHostController){
    Card(modifier = Modifier
        .padding(4.dp)
        .clickable(onClick = {navController.navigate("Chat")}),
        elevation = 4.dp) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Rounded.Person,"Person Icon",
                modifier = Modifier
                    .fillMaxHeight()
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(message.sender,Modifier.weight(1f), fontSize = 20.sp)
                    Text(message.type, fontSize = 15.sp)
                }
                Text(message.text, fontSize = 15.sp)
            }
        }
    }
}
