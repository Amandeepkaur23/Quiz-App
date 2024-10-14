package com.example.quizappassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quizappassignment.di.Converter
import com.example.quizappassignment.model.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class QuizDatabase: RoomDatabase() {
    abstract fun quizDao(): QuizDao
}