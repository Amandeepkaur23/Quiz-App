package com.example.quizappassignment.di

import android.content.Context
import com.example.quizappassignment.api.QuizApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://opentdb.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideQuizApi(retrofit: Retrofit): QuizApi {
        return retrofit.create(QuizApi::class.java)
    }

    @Provides
    fun provideAppContext( @ApplicationContext appContext: Context): Context{
        return appContext
    }
}