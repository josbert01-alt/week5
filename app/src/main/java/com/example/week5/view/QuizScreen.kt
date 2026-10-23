package com.example.week5.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5.ui.theme.Week5Theme
import com.example.week5.viewmodel.QuizViewModel

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    if (viewModel.page == "start") {
        StartPage(viewModel, modifier)
    } else if (viewModel.page == "quiz") {
        QuestionPage(viewModel, modifier)
    } else {
        ResultPage(viewModel, modifier)
    }
}

@Composable
fun StartPage(viewModel: QuizViewModel, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Mini Quiz", fontSize = 24.sp)
        Text(text = "How many questions do you want? (1 - " + viewModel.totalQuestions + ")")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.numberInput,
            onValueChange = { viewModel.onNumberChange(it) },
            label = { Text("Number of questions") },
            isError = viewModel.numberError != "",
            supportingText = {
                if (viewModel.numberError != "") {
                    Text(viewModel.numberError)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.startQuiz() },
            enabled = viewModel.isNumberValid(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Quiz")
        }
    }
}

@Composable
fun QuestionPage(viewModel: QuizViewModel, modifier: Modifier) {
    val question = viewModel.getCurrentQuestion()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Question " + (viewModel.currentIndex + 1), fontSize = 24.sp)
        Text(text = question.question, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(16.dp))

        for (option in question.options) {
            val isSelected = viewModel.selectedAnswer == option

            var buttonColor = MaterialTheme.colorScheme.secondaryContainer
            var textColor = MaterialTheme.colorScheme.onSecondaryContainer
            if (isSelected) {
                buttonColor = MaterialTheme.colorScheme.primary
                textColor = MaterialTheme.colorScheme.onPrimary
            }

            Button(
                onClick = { viewModel.selectAnswer(option) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = textColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.nextQuestion() },
            enabled = viewModel.selectedAnswer != "",
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}

@Composable
fun ResultPage(viewModel: QuizViewModel, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Quiz Finished!", fontSize = 24.sp)
        Text(text = "Your Score: " + viewModel.score + " / " + viewModel.quizQuestions.size)

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.wrongAnswers.size == 0) {
            Text(
                text = "Perfect! No wrong answers.",
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            for (wrong in viewModel.wrongAnswers) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = "Q" + wrong.number + ": " + wrong.question, fontSize = 14.sp)
                    Text(
                        text = "Your Answer: " + wrong.yourAnswer,
                        color = Color(0xFFC62828),
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Correct Answer: " + wrong.correctAnswer,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp
                    )
                }
                HorizontalDivider()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.restartQuiz() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Restart Quiz")
        }

        TextButton(onClick = { viewModel.backToStart() }) {
            Text("Change Number of Questions")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartPagePreview() {
    val viewModel = QuizViewModel()

    Week5Theme {
        QuizScreen(viewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun StartPageErrorPreview() {
    val viewModel = QuizViewModel()
    viewModel.onNumberChange("6")

    Week5Theme {
        QuizScreen(viewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionPagePreview() {
    val viewModel = QuizViewModel()
    viewModel.onNumberChange("3")
    viewModel.startQuiz()
    viewModel.selectAnswer(viewModel.getCurrentQuestion().options[2])

    Week5Theme {
        QuizScreen(viewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun ResultPagePreview() {
    val viewModel = QuizViewModel()
    viewModel.onNumberChange("3")
    viewModel.startQuiz()

    for (i in 0 until 3) {
        viewModel.selectAnswer(viewModel.getCurrentQuestion().options[0])
        viewModel.nextQuestion()
    }

    Week5Theme {
        QuizScreen(viewModel = viewModel)
    }
}
