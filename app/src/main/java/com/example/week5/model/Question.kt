package com.example.week5.model

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

data class WrongAnswer(
    val number: Int,
    val question: String,
    val yourAnswer: String,
    val correctAnswer: String
)

object QuestionData {
    val questions = listOf(
        Question(
            "Ibu Kota Di Indonesia?",
            listOf("Surabaya", "Bandung", "Jakarta", "Medan"),
            "Jakarta"
        ),
        Question(
            "Ada berapa hari dalam seminggu?",
            listOf("5", "7", "10", "11"),
            "7"
        ),
        Question(
            "Mata Kuliah VP pakai apa?",
            listOf("Word", "Excel", "Android Studio", "Photoshop"),
            "Android Studio"
        ),
        Question(
            "Apakah Mata Kuliah VP susah?",
            listOf("Sangat Sulit", "Sulit", "Mudah", "Sangat Mudah"),
            "Sangat Mudah"
        ),
        Question(
            "Natal dirayakan pada bulan?",
            listOf("Agustus", "Oktober", "November", "Desember"),
            "Desember"
        )
    )
}
