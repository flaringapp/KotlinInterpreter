package interpreter.parser

import interpreter.expression.nonTerminal.loop.WhileExpression
import interpreter.parser.utils.*

object WhileParser {

    fun parse(code: Code): WhileExpression {
        val whileConditionLine = code.asLineBeforeStatement()

        val conditionLine = whileConditionLine.substring(
            whileConditionLine.indexOf(BRACKET_START) + 1,
            whileConditionLine.lastIndexOf(BRACKET_END)
        ).trimEndSpaces()

        val iterationCode = code.codeInsideStatement()

        val condition = LineParser.parse(conditionLine)
            ?: throw IllegalStateException("Missing condition in while statement:\n${code.toLine()}")

        val iterationAction = BlockParser.parse(iterationCode)

        return WhileExpression(
            condition,
            iterationAction
        )
    }

}