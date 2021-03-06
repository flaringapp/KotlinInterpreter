package com.flaringapp.mapzlab1.interpreter.exceptions

class InvalidSemicolonException(
    val line: String,
    val index: Int
) : ParserException("Invalid semicolon at index $index in line $line")