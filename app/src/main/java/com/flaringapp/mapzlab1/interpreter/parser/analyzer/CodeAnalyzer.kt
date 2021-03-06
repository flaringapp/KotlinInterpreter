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
            isIf() -> getNextIf().typed(ExpressionType.IF)
            else -> getNextLine().asCode().typed(ExpressionType.LINE)
        }.also {
            parsingCode.removeFirst(it.code.size)
        }
    }

    private fun getNextFor(): Code {
        val statementIndices = parsingCode.indicesOfFirstStatement()
        val lastLine = parsingCode[statementIndices.second]

        parsingCode.inlineCodeAfterStatementEnd(lastLine, statementIndices.second)

        return parsingCode.subList(0, statementIndices.second + 1)
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

        val whileStatement = parsingCode[statementIndices.second + 1]
        if (!whileStatement.startsWith(WHILE)) {
            throw IllegalStateException("Missing while condition in do expression: $whileStatement")
        }

        val whileConditionIndices = parsingCode.indicesOfFirstBrackets()

        return parsingCode.subList(0, whileConditionIndices.second + 1)
    }

    private fun getNextIf(): Code {
        val ifStatements: MutableList<Code> = mutableListOf()
        var elseStatement: Code? = null

        var lastStatementEnd = -1

        var workingCode = parsingCode

        while (true) {
            workingCode = workingCode.subList(lastStatementEnd + 1, workingCode.size)
            val statementIndices = workingCode.indicesOfFirstStatement()
            val lastLine = workingCode[statementIndices.second]

            workingCode.inlineCodeAfterStatementEnd(lastLine, statementIndices.second)

            workingCode.subList(0, statementIndices.second + 1).toList().let {
                if (workingCode[0].startsWith(IF) || workingCode[0].startsWith(ELSE_IF)) {
                    ifStatements += it
                } else if (workingCode[0].startsWith(ELSE)) {
                    elseStatement = it
                }
            }

            lastStatementEnd = statementIndices.second

            if (statementIndices.second + 1 >= workingCode.size) break
            if (!workingCode[statementIndices.second + 1].startsWith(ELSE) &&
                !workingCode[statementIndices.second + 1].startsWith(ELSE_IF)
            ) break
        }

        return if (elseStatement == null) ifStatements.flatten()
        else ifStatements.flatten() + elseStatement!!
    }

    private fun getNextLine(): String {
        return parsingCode[0]
    }

    private fun isFor() = parsingCode.first().startsWith(FOR)

    private fun isWhile() = parsingCode.first().startsWith(WHILE)

    private fun isDoWhile() = parsingCode.first().startsWith(DO)

    private fun isIf() = parsingCode.first().startsWith(IF)

    private fun MutableList<String>.inlineCodeAfterStatementEnd(
        statementEndLine: String,
        lineIndex: Int
    ) {
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