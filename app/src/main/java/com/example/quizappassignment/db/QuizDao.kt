package com.example.quizappassignment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizappassignment.model.Result

@Dao
interface QuizDao {
    @Insert
    suspend fun insertQuiz(quiz: List<Result>)

    @Query("SELECT * FROM quizTable WHERE question = :question")
    suspend fun getQuizByQuestion(question: String): Result?

    @Query("SELECT * FROM quizTable")
    fun getAllQuizzes(): List<Result>

    // Add a delete method if needed
    @Query("DELETE FROM quizTable WHERE question = :question")
    suspend fun deleteQuizByQuestion(question: String)

}