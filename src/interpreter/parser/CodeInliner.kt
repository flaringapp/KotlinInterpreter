package interpreter.parser

import interpreter.exceptions.InvalidSemicolonException
import interpreter.exceptions.InvalidStatementLevelException

fun Code.inline(): Code {
    return flatMap {
        it.trimSpaces().inlineSemicolons()
    }
}

private fun String.inlineSemicolons(): List<String> {
    if (isEmpty()) return emptyList()

    val inlined = mutableListOf<String>()

    var currentStatementLevel = 0

    var currentLineStart = 0

    forEachIndexed { index, char ->
        when (char) {
            SEMICOLON -> {
                if (currentLineStart == index) throw InvalidSemicolonException(this, index)

                if (currentStatementLevel == 0)  {
                    inlined.add(substring(currentLineStart, index))
                    currentLineStart = index + 1
                }
            }
            STATEMENT_START -> currentStatementLevel++
            STATEMENT_END -> {
                currentStatementLevel--
                if (currentStatementLevel < 0) throw InvalidStatementLevelException(this, index)
            }
        }
    }

    if (currentLineStart != length) {
        inlined.add(substring(currentLineStart, length))
    }

    return inlined.map { it.trimSpaces() }
}