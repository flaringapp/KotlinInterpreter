package com.flaringapp.mapzlab1.interpreter.exceptions

class UnsupportedStatementTypeException(
    val statement: String
) : ParserException("Unsupported statement type : $statement")