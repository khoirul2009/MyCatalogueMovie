package com.mymovie.core.utils

import androidx.room.TypeConverter

class ArrayConverter {

    @TypeConverter
    fun fromIntArray(array: List<Int>): String {
        return array.joinToString(",")
    }

    @TypeConverter
    fun toIntArray(data: String): List<Int> {
        return data.split(",").map { it.toInt() }
    }
}