package com.flaringapp.mapzlab1.interpreter.exceptions

import com.flaringapp.mapzlab1.interpreter.parser.utils.STATEMENT_END

class InvalidStatementLevelException (
    val line: String,
    val index: Int
): ParserException("Invalid statement closure (\'$STATEMENT_END\') at index $index in line $line")