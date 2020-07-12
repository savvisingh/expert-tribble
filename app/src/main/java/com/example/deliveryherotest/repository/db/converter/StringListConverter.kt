package com.example.deliveryherotest.repository.db.converter

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun stringAsStringList(strings: String?): List<String> {
        val list = mutableListOf<String>()
        strings
            ?.split(",")
            ?.forEach {
                list.add(it)
            }

        return list
    }

    @TypeConverter
    fun stringListAsString(strings: List<String>?): String {
        var result = ""
        strings?.forEach { element ->
            result += "$element,"
        }
        return result.removeSuffix(",")
    }
}