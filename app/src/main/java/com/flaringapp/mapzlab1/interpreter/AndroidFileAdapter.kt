package com.flaringapp.mapzlab1.interpreter

import android.content.Context
import com.flaringapp.mapzlab1.R

class AndroidFileAdapter {

    fun readInputFile(context: Context): List<String> {
        val inputStream = context.resources.openRawResource(R.raw.code)
        return inputStream.bufferedReader().use { it.readLines() }
    }

}