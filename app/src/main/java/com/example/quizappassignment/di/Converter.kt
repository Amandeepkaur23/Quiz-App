package com.example.quizappassignment.di

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value) // Convert List<String> to JSON String
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType) // Convert JSON String back to List<String>
    }
}