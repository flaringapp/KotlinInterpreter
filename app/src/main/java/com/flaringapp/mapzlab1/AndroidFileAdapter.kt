package com.flaringapp.mapzlab1

import android.content.Context

object AndroidFileAdapter {

    fun readInputFile(context: Context): List<String> {
        val inputStream = context.resources.openRawResource(R.raw.code)
        return inputStream.bufferedReader().use { it.readLines() }
    }

}