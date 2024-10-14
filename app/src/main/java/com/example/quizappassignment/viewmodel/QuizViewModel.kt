package com.example.quizappassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizappassignment.model.Questions
import com.example.quizappassignment.model.Result
import com.example.quizappassignment.repository.QuizRepository
import com.example.quizappassignment.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository
): ViewModel(){

    val quiz: LiveData<NetworkResult<Questions>>
        get() = quizRepository.quiz

    fun fetchQuiz(){
        viewModelScope.launch {
            try {
                // Fetch the quiz from the repository
                quizRepository.getQuiz()
            } catch (e: Exception) {
                Log.d("QuizViewModel", "Error fetching quiz: ${e.message}")
            }
        }
    }
}