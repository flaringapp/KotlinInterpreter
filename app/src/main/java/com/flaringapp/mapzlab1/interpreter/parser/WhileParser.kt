package com.flaringapp.mapzlab1.interpreter.parser

import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop.WhileExpression
import com.flaringapp.mapzlab1.interpreter.parser.utils.*

object WhileParser {

    fun parse(code: Code): WhileExpression {
        val whileConditionLine = code.asLineBeforeStatement()

        val conditionLine = whileConditionLine.substring(
            whileConditionLine.indexOf(BRACKET_START) + 1,
            whileConditionLine.lastIndexOf(BRACKET_END)
        ).trimSpaces()

        val iterationCode = code.codeInsideStatement()

        val condition = LineParser.parse(conditionLine)
            ?: throw IllegalStateException("Missing condition in while statement:\n${code.format()}")

        val iterationAction = BlockParser.parse(iterationCode)

        return WhileExpression(
            condition,
            iterationAction
        )
    }

}