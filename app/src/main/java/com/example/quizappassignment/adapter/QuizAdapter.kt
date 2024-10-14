package com.example.quizappassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quizappassignment.R
import com.example.quizappassignment.databinding.QuizItemlistBinding
import com.example.quizappassignment.model.Result

class QuizAdapter(private val onScoreUpdate: (Int) -> Unit): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    private var quizList: List<Result> = emptyList()
    private var userScore = 0

    class QuizViewHolder(private val binding: QuizItemlistBinding): RecyclerView.ViewHolder(binding.root) {

        fun binds(quiz: Result, onOptionClick: (Boolean) -> Unit){
            binding.question.text = quiz.question
            val options = listOf(
                binding.optionA.apply { text = "A: ${quiz.incorrect_answers[0]}" },
                binding.optionB.apply { text = "B: ${quiz.incorrect_answers[1]}" },
                binding.optionC.apply { text = "C: ${quiz.correct_answer}" },
                binding.optionD.apply { text = "D: ${quiz.incorrect_answers[2]}" }
            )

            // Reset all options to default state
            options.forEach {
                it.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))  // Assuming black is the default text color
                it.isClickable = true  // Enable all options for clicking again
            }

            // Handle click events for each option
            options.forEachIndexed { index, textView ->
                textView.setOnClickListener {
                    // Disable all options after a click
                    options.forEach { it.isClickable = false }

                    // Check if the clicked option is the correct one
                    val isCorrect = (index == 2) // Assuming index 2 is correct since quiz.correct_answer is at option C

                    if (isCorrect) {
                        textView.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
                    } else {
                        textView.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
                        // Highlight correct answer
                        options[2].setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
                    }
                    // Call the callback function to notify MainActivity
                    onOptionClick(isCorrect)
                    //textView.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = QuizItemlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizList[position]
        // Bind the data and handle clicks
        holder.binds(quiz) { isCorrect ->
            if (isCorrect) {
                userScore += 5 // Increment score by 5 if correct
            }
            onScoreUpdate(userScore) // Notify MainActivity with updated score
        }
    }

    // Update the RecyclerView with new data
    fun updateData(newQuizList: List<Result>) {
        quizList = newQuizList
        notifyDataSetChanged()
    }


}