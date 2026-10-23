package com.example.week5.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week5.model.Question
import com.example.week5.model.QuestionData
import com.example.week5.model.WrongAnswer

class QuizViewModel : ViewModel() {

    var page by mutableStateOf("start")

    val totalQuestions = QuestionData.questions.size
    var numberInput by mutableStateOf("")
    var numberError by mutableStateOf("")

    val quizQuestions = mutableStateListOf<Question>()
    var currentIndex by mutableStateOf(0)
    var selectedAnswer by mutableStateOf("")

    private var questionCount = 3

    var score by mutableStateOf(0)
    val wrongAnswers = mutableStateListOf<WrongAnswer>()

    fun onNumberChange(text: String) {
        numberInput = text
        numberError = ""

        if (text == "") {
            return
        }

        val number = text.toIntOrNull()
        if (number == null) {
            numberError = "Please input a number"
        } else if (number < 1) {
            numberError = "Minimum is 1 question"
        } else if (number > totalQuestions) {
            numberError = "Maximum is $totalQuestions questions"
        }
    }

    fun isNumberValid(): Boolean {
        return numberInput != "" && numberError == ""
    }

    fun startQuiz() {
        questionCount = numberInput.toInt()
        setupQuiz()
    }

    private fun setupQuiz() {
        quizQuestions.clear()
        val randomQuestions = QuestionData.questions.shuffled()
        for (i in 0 until questionCount) {
            quizQuestions.add(randomQuestions[i])
        }

        currentIndex = 0
        selectedAnswer = ""
        score = 0
        wrongAnswers.clear()
        page = "quiz"
    }

    fun getCurrentQuestion(): Question {
        return quizQuestions[currentIndex]
    }

    fun selectAnswer(answer: String) {
        selectedAnswer = answer
    }

    fun nextQuestion() {
        val question = getCurrentQuestion()

        if (selectedAnswer == question.correctAnswer) {
            score = score + 1
        } else {
            wrongAnswers.add(
                WrongAnswer(
                    currentIndex + 1,
                    question.question,
                    selectedAnswer,
                    question.correctAnswer
                )
            )
        }

        if (currentIndex == quizQuestions.size - 1) {
            page = "result"
        } else {
            currentIndex = currentIndex + 1
            selectedAnswer = ""
        }
    }

    fun restartQuiz() {
        setupQuiz()
    }

    fun backToStart() {
        numberInput = ""
        numberError = ""
        page = "start"
    }
}
