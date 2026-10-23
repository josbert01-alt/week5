package com.example.week5.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week5.ui.theme.Week5Theme

@Composable
fun MenuScreen(
    onExpenseClick: () -> Unit,
    onQuizClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Week 5", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onExpenseClick, modifier = Modifier.fillMaxWidth()) {
            Text("Soal 1 - Expenses")
        }
        Button(onClick = onQuizClick, modifier = Modifier.fillMaxWidth()) {
            Text("Soal 2 - Quiz")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    Week5Theme {
        MenuScreen(onExpenseClick = {}, onQuizClick = {})
    }
}
