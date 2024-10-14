package com.example.quizappassignment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizappassignment.model.Result

@Dao
interface QuizDao {
    @Insert
    suspend fun insertQuiz(quiz: List<Result>)

    @Query("SELECT * FROM quiztable")
    fun getAllQuizzes(): List<Result>

}