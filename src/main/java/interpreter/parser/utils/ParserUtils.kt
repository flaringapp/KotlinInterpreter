package interpreter.parser.utils

import interpreter.parser.*

val spaces = charArrayOf(' ', '\t')

fun String.trimSpaces() = trim(*spaces)
fun String.trimStartSpaces() = trimStart(*spaces)
fun String.trimEndSpaces() = trimStart(*spaces)

fun String.isDigitsOnly() = find { !it.isDigit() } == null
fun String.isLettersOnly() = find { !it.isLetter() } == null

fun String.startsWithDigit() = isEmpty() || first().isDigit()
fun String.endsWithDigit() = isEmpty() || last().isDigit()

fun String.isDigitsOrLetters() = find { !it.isDigit() && !it.isLetter() } == null

fun String.isInt() = isDigitsOnly()
fun String.isBool() = this == TRUE || this == FALSE
fun String.isString() = length >= 2 && startsWith(QUOTE) && endsWith(
    QUOTE
)

fun String.isVariable() = !startsWithDigit() && substring(1, length).isDigitsOrLetters()

fun String.extractString() = substring(indexOfFirst { it == QUOTE } + 1, indexOfLast { it == QUOTE })

fun Code.toLine() = reduce { acc, line ->
    acc + NEW_LINE + line.trimSpaces()
}

fun Code.asLineBeforeStatement(): String {
    val statementStartLineIndex = indexOfFirst { it.contains(STATEMENT_START) }
        .takeIf { it >= 0 }
        ?: throw IllegalStateException("There is no statement in code:\n${toLine()}")
    val statementStartLine = this[statementStartLineIndex]

    val codeBeforeStatement = this.subList(0, statementStartLineIndex)
        .toMutableList()

    codeBeforeStatement += statementStartLine.substring(0, statementStartLine.indexOf(STATEMENT_START))
        .trimEndSpaces()

    return codeBeforeStatement.toLine()
}

fun Code.indicesOfFirstStatement(): Pair<Int, Int> {
    val statementStartLineIndex = indexOfFirst { it.contains(STATEMENT_START) }
        .takeIf { it >= 0 }
        ?: throw IllegalStateException("There is no statement in code:\n${toLine()}")

    var currentStatementLevel = 0
    for (i in statementStartLineIndex until size) {
        this[i].forEach {
            when (it) {
                STATEMENT_START -> currentStatementLevel++
                STATEMENT_END -> {
                    currentStatementLevel--
                    if (currentStatementLevel == 0) return statementStartLineIndex to i
                }
            }
        }
    }

    if (currentStatementLevel == 0) return statementStartLineIndex to size - 1

    throw IllegalStateException("Invalid statement in code\n${this.toLine()}")
}

fun Code.codeInsideStatement(): Code {
    val indicesOfStatement = indicesOfFirstStatement()

    val statementStartLine = this[indicesOfStatement.first]
    val statementEndLine = this[indicesOfStatement.second]

    val codeInsideStatement = subList(indicesOfStatement.first + 1, indicesOfStatement.second)
        .toMutableList()

    val statementStartIndex = statementStartLine.indexOf(STATEMENT_START)
    if (statementStartIndex < statementStartLine.length - 1) {
        val lineAfterStatementStart = statementStartLine.substring(statementStartIndex + 1)
            .trimSpaces()
        if (lineAfterStatementStart.isNotEmpty()) {
            codeInsideStatement.add(0, lineAfterStatementStart)
        }
    }

    val statementEndIndex = statementEndLine.indexOf(STATEMENT_END)
    if (statementEndIndex > 0) {
        val lineBeforeStatementEnd = statementEndLine.substring(0, statementEndIndex - 1)
            .trimSpaces()
        if (lineBeforeStatementEnd.isNotEmpty()) {
            codeInsideStatement += lineBeforeStatementEnd
        }
    }

    return codeInsideStatement
}