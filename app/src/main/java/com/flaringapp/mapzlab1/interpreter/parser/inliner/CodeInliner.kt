package com.flaringapp.mapzlab1.interpreter.parser.inliner

import com.flaringapp.mapzlab1.interpreter.exceptions.InvalidSemicolonException
import com.flaringapp.mapzlab1.interpreter.parser.*
import com.flaringapp.mapzlab1.interpreter.parser.utils.*

fun Code.inline(): Code {
    return removeEmptyLines()
        .flatMap {
            it.trimSpaces().inlineSemicolons()
        }
}

private fun List<String>.removeEmptyLines() = filter {
    it.trimSpaces().isNotEmpty()
}

private fun String.inlineSemicolons(): List<String> {
    if (isEmpty()) return emptyList()

    val inlined = mutableListOf<String>()

    var currentBracketLevel = 0

    var currentLineStart = 0

    forEachIndexed { index, char ->
        when (char) {
            SEMICOLON -> {
                if (currentLineStart == index) throw InvalidSemicolonException(this, index)

                if (currentBracketLevel == 0) {
                    inlined.add(substring(currentLineStart, index))
                    currentLineStart = index + 1
                }
            }
            BRACKET_START -> currentBracketLevel++
            BRACKET_END -> {
                currentBracketLevel--
            }
        }
    }

    if (currentLineStart != length) {
        inlined.add(substring(currentLineStart, length))
    }

    return inlined.map { it.trimSpaces() }
}