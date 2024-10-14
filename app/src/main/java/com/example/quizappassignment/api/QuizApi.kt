package com.example.quizappassignment.api

import com.example.quizappassignment.model.Questions
import retrofit2.Response
import retrofit2.http.GET

interface QuizApi {

    @GET("/api.php?amount=20&category=18&difficulty=medium&type=multiple")
    suspend fun getQuiz(): Response<Questions>
}

//https://opentdb.com/api.php?amount=20&category=18&difficulty=medium&type=multiple