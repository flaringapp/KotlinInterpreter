package interpreter.parser.inliner

import interpreter.exceptions.InvalidSemicolonException
import interpreter.exceptions.InvalidStatementLevelException
import interpreter.parser.*
import interpreter.parser.utils.*

fun Code.inline(): Code {
    return flatMap {
        it.trimSpaces().inlineSemicolons()
    }
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
                if (currentBracketLevel < 0) throw InvalidStatementLevelException(this, index)
            }
        }
    }

    if (currentLineStart != length) {
        inlined.add(substring(currentLineStart, length))
    }

    return inlined.map { it.trimSpaces() }
}