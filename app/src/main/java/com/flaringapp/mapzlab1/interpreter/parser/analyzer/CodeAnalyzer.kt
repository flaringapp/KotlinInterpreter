package com.flaringapp.mapzlab1.interpreter.parser.analyzer

import com.flaringapp.mapzlab1.interpreter.parser.Code
import com.flaringapp.mapzlab1.interpreter.parser.utils.*

class CodeAnalyzer(initialCode: Code) {

    private val parsingCode = initialCode.toMutableList()

    fun isEmpty() = parsingCode.isEmpty()
    fun isNotEmpty() = !isEmpty()

    fun getNextBlock(): TypedCode {
        return when {
            isFor() -> getNextFor().typed(ExpressionType.FOR)
            isWhile() -> getNextWhile().typed(ExpressionType.WHILE)
            isDoWhile() -> getNextDoWhile().typed(ExpressionType.DO_WHILE)
            else -> getNextLine().asCode().typed(ExpressionType.LINE)
        }.also {
            parsingCode.removeFirst(it.code.size)
        }
    }

    private fun getNextFor(): Code {
        val statementIndices = parsingCode.indicesOfFirstStatement()
        val lastLine = parsingCode[statementIndices.second]

        parsingCode.inlineCodeAfterStatementEnd(lastLine, statementIndices.second)

        return parsingCode.subList(0, statementIndices.second)
    }

    private fun getNextWhile(): Code {
        val statementIndices = parsingCode.indicesOfFirstStatement()
        val lastLine = parsingCode[statementIndices.second]

        parsingCode.inlineCodeAfterStatementEnd(lastLine, statementIndices.second)

        return parsingCode.subList(0, statementIndices.second + 1)
    }

    private fun getNextDoWhile(): Code {
        val statementIndices = parsingCode.indicesOfFirstStatement()
        val lastLine = parsingCode[statementIndices.second]

        parsingCode.inlineCodeAfterStatementEnd(lastLine, statementIndices.second)

        val whileStatement  = parsingCode[statementIndices.second + 1]
        if (!whileStatement.startsWith(WHILE)) {
            throw IllegalStateException("Missing while condition in do expression: $whileStatement")
        }

        val whileConditionIndices = parsingCode.indicesOfFirstBrackets()

        return parsingCode.subList(0, whileConditionIndices.second + 1)
    }

    private fun getNextLine(): String {
        return parsingCode[0]
    }

    private fun isFor() = parsingCode.first().startsWith(FOR)

    private fun isWhile() = parsingCode.first().startsWith(WHILE)

    private fun isDoWhile() = parsingCode.first().startsWith(DO)

    private fun MutableList<String>.inlineCodeAfterStatementEnd(statementEndLine: String, lineIndex: Int) {
        val statementEndIndex = statementEndLine.indexOf(STATEMENT_END)
        if (statementEndIndex < statementEndLine.length - 1) {
            this[lineIndex] = statementEndLine.substring(0, statementEndIndex + 1)
            add(lineIndex + 1, statementEndLine.substring(statementEndIndex + 1).trimStartSpaces())
        }
    }

    private inline fun <reified T> MutableList<T>.removeFirst(count: Int) {
        removeRange(0, count)
    }

    private inline fun <reified T> MutableList<T>.removeRange(from: Int, count: Int) {
        for (i in from until from + count) {
            removeAt(from)
        }
    }
}