package com.example.quizappassignment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quizappassignment.adapter.QuizAdapter
import com.example.quizappassignment.databinding.FragmentQuizBinding
import com.example.quizappassignment.util.NetworkResult
import com.example.quizappassignment.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var score = 0

    private val quizViewModel: QuizViewModel by viewModels()
    private lateinit var quizAdapter: QuizAdapter

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("QuizAppPrefs", Context.MODE_PRIVATE)

        // Initialize RecyclerView
        quizAdapter = QuizAdapter { updatedScore ->
            score = updatedScore
            Log.d("score", score.toString())
        }
        binding.quizRv.adapter = quizAdapter
        quizViewModel.fetchQuiz()

        //Set timer
        val timer = object : CountDownTimer(60000, 1000) {  // 1 minutes countdown with 1 second interval
            override fun onTick(millisUntilFinished: Long) {
                // Update the UI with the remaining time
                binding.txtTimer.text = "Minutes remaining: ${millisUntilFinished / 1000} mins"
            }

            override fun onFinish() {
                // Action when the timer finishes
                saveHighScore(score)  // Save the high score before navigating to the result screen
                val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(score)
                findNavController().navigate(action)
            }
        }

        quizViewModel.quiz.observe(viewLifecycleOwner, Observer {
            binding.txtLoading.isVisible = true
            when(it){
                is NetworkResult.Error -> {
                    binding.txtLoading.isVisible = false
                }

                is NetworkResult.Success -> {
                    quizAdapter.updateData(it.data!!.results)
                    binding.txtLoading.isVisible = false
                    timer.start()  // Start the timer
                }

                is NetworkResult.Loading -> {
                    binding.txtLoading.isVisible = true
                }

            }
        })

    }

    private fun saveHighScore(currentScore: Int) {
        // Get the stored high score
        val highScore = sharedPreferences.getInt("HIGH_SCORE", 0)

        // Check if the current score is higher than the stored high score
        if (currentScore > highScore) {
            // Save the new high score
            sharedPreferences.edit().putInt("HIGH_SCORE", currentScore).apply()
            Log.d("HighScore", "New High Score: $currentScore")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}