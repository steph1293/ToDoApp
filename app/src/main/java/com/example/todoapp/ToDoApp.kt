package com.example.todoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

val DarkGreen = Color(0xFF006400)
val DarkBlue = Color(0xFF00008B)

@Composable
fun ToDoApp() {
    val tasks = remember { mutableStateListOf<Task>() }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "My To-Do List",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White // Change text color here
                )
            }
            TaskInput { taskName, taskDescription ->
                if (taskName.isNotEmpty()) {
                    tasks.add(Task(taskName, taskDescription))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TaskList(tasks = tasks)
        }
    }
}

@Composable
fun TaskInput(onTaskAdded: (String, String) -> Unit) {
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("")
    }
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = taskName,
            onValueChange = {
                taskName = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            placeholder = { Text("Enter your task for today") }
        )
        TextField(
            value = taskDescription,
            onValueChange = {
                taskDescription = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            placeholder = { Text("Enter your description") }
        )
        Button(
            onClick = {
                if (taskName.isNotEmpty() && taskDescription.isNotEmpty()) {
                    onTaskAdded(taskName, taskDescription)
                    taskName = ""
                    taskDescription = ""
                } else {
                    Toast.makeText(
                        context,
                        "Task name and description cannot be empty!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
        }
    }
}

@Composable
fun TaskList(tasks: List<Task>) {
    LazyColumn { items(tasks) { task -> TaskItem(task) } }
}

@Composable
fun TaskItem(task: Task) {
    var deleted by remember { mutableStateOf(false) }
    if (!deleted) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(task.name, color = DarkGreen)
                Text(task.description, style = MaterialTheme.typography.bodyMedium,
                    color = DarkBlue)
            }
            Button(onClick = { deleted = true }) {
                Text("Delete")
            }
        }
    }
}

