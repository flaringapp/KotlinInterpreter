package com.flaringapp.mapzlab1.interpreter.parser

import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop.DoExpression
import com.flaringapp.mapzlab1.interpreter.parser.utils.*

object DoWhileParser {

    fun parse(code: Code): DoExpression {
        val iterationCode = code.codeInsideStatement()

        val indicesOfIterationStatement = code.indicesOfFirstStatement()
        val iterationStatementLastLine = code[indicesOfIterationStatement.second]

        var whileCode = iterationStatementLastLine.substring(
            iterationStatementLastLine.indexOf(STATEMENT_END)
        )
        if (indicesOfIterationStatement.second < code.size - 1) {
            whileCode += code.subList(indicesOfIterationStatement.second + 1, code.size).toLine()
        }

        val whileKeyIndex = whileCode.indexOf(WHILE)
        val whileConditionsLine = whileCode.substring(whileKeyIndex + WHILE.length)
        val conditionLine = whileConditionsLine.substring(
            whileConditionsLine.indexOf(BRACKET_START) + 1,
            whileConditionsLine.lastIndexOf(BRACKET_END)
        ).trimSpaces()

        val loopCondition = LineParser.parse(conditionLine)
            ?: throw IllegalStateException("$conditionLine is not bool expression")

        val iterationAction = BlockParser.parse(iterationCode)

        return DoExpression(
            iterationAction,
            loopCondition
        )
    }

}