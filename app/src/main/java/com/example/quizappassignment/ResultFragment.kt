package com.example.quizappassignment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.quizappassignment.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("QuizAppPrefs", Context.MODE_PRIVATE)

        // Retrieve the score from the arguments
        val score = arguments?.getInt("score") ?: 0

        // Display the score
        binding.txtScore.text = "Your score is: $score"

        // Retrieve and display the high score
        val highScore = sharedPreferences.getInt("HIGH_SCORE", 0)
        binding.txtHighScore.text = "High Score: $highScore"
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}