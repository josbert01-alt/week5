package com.example.week5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.week5.ui.theme.Week5Theme
import com.example.week5.view.ExpenseScreen
import com.example.week5.view.MenuScreen
import com.example.week5.view.QuizScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week5Theme {
                var currentPage by remember { mutableStateOf("menu") }

                BackHandler(enabled = currentPage != "menu") {
                    currentPage = "menu"
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (currentPage == "expense") {
                        ExpenseScreen(modifier = Modifier.padding(innerPadding))
                    } else if (currentPage == "quiz") {
                        QuizScreen(modifier = Modifier.padding(innerPadding))
                    } else {
                        MenuScreen(
                            onExpenseClick = { currentPage = "expense" },
                            onQuizClick = { currentPage = "quiz" },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
