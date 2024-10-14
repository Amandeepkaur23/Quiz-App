package com.example.quizappassignment.di

import android.content.Context
import androidx.room.Room
import com.example.quizappassignment.db.QuizDao
import com.example.quizappassignment.db.QuizDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuizDatabase {
        return Room.databaseBuilder(context = context,
            QuizDatabase::class.java,
            "QuizDB").build()
    }

    @Provides
    fun provideQuizDao(database: QuizDatabase): QuizDao {
        return database.quizDao()
    }
}