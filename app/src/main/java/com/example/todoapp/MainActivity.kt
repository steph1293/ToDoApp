package com.example.todoapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                Scaffold( modifier = Modifier.fillMaxSize(), content = {
                    ToDoApp()
                } )
            }
        }
    }
}

@Composable
fun ToDoApp() {
    val tasks = remember { mutableStateListOf<Task>() }
    Column( modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))

    {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top= 25.dp, bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) { Text(
            text = "My To-Do List",
            style = MaterialTheme.typography.titleLarge) }
        TaskInput {
            taskName -> if (taskName.isNotEmpty()) { tasks.add(Task(taskName)) } }
        Spacer(modifier = Modifier.height(8.dp))
        TaskList(tasks = tasks) }
}
@Composable
fun TaskInput(onTaskAdded: (String) -> Unit) {
    var taskName by remember { mutableStateOf("") }
    Row {
        TextField( value = taskName,
            onValueChange = {
        taskName = it
                            },
            modifier = Modifier.weight(1f), placeholder = {
                Text("Enter your task for today") } )
        Button(
            onClick = {
                onTaskAdded(taskName)
                taskName = ""
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text("Add")
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
        ) { Text(task.name)
            Button (onClick = { deleted = true }) { Text("Delete") } }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoApp()
}