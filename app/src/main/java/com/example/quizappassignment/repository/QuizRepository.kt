package com.example.quizappassignment.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizappassignment.api.QuizApi
import com.example.quizappassignment.db.QuizDao
import com.example.quizappassignment.model.Questions
import com.example.quizappassignment.util.NetworkResult
import com.example.quizappassignment.util.NetworkUtil
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val quizApi: QuizApi,
    private val quizDao: QuizDao,
    private val networkUtil: NetworkUtil,
    private val appContext: Context
) {

    private val _quiz = MutableLiveData<NetworkResult<Questions>>()
    val quiz: LiveData<NetworkResult<Questions>> get() = _quiz

    suspend fun getQuiz(){
        if(networkUtil.isInternetAvailable(context = appContext)){
            try {
                val result = quizApi.getQuiz()
                if (result.isSuccessful && result.body() != null) {
                    _quiz.postValue(NetworkResult.Success(result.body()!!))
                    Log.d("QuizRepository", "Quiz data fetched successfully: ${result.body()}")
                    quizDao.insertQuiz(result.body()!!.results)
                } else {
                    _quiz.postValue(NetworkResult.Error("something went wrong"))
                    Log.d("QuizRepository", "Failed to fetch quiz: ${result.errorBody()?.string()}")
                }
            }
            catch (e: Exception){
                Log.d("QuizRepository", "Error fetching quiz: ${e.message}")
                _quiz.postValue(NetworkResult.Error(e.message))
            }
        } else {
            try{

                val result = quizDao.getAllQuizzes()
                val questions = Questions(0, result)
                _quiz.postValue(NetworkResult.Success(questions))
                Log.d("QuizRepository", "Quiz data fetched successfully: $questions")
            }
            catch (e:Exception){
                Log.d("QuizRepository", "Error fetching quiz: ${e.message}")
                _quiz.postValue(NetworkResult.Error(e.message))
            }

        }

    }
}