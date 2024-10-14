package com.example.quizappassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizTable")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category: String,
    val correct_answer: String,
    val difficulty: String,
    val incorrect_answers: List<String>,
    val question: String,
    val type: String
)