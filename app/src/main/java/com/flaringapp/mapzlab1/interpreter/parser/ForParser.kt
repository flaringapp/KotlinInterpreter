package com.flaringapp.mapzlab1.interpreter.parser

import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop.ForExpression
import com.flaringapp.mapzlab1.interpreter.parser.utils.*

object ForParser {

    fun parse(code: Code): ForExpression {
        val forConditionsLine = code.asLineBeforeStatement()

        val conditionsLine = forConditionsLine.substring(
            forConditionsLine.indexOf(BRACKET_START) + 1,
            forConditionsLine.lastIndexOf(BRACKET_END)
        ).trimEndSpaces()

        val iterationCode = code.codeInsideStatement()

        val conditions = conditionsLine.split(SEMICOLON)
            .map { it.trimSpaces() }
            .also {
                if (it.size != 3) throw IllegalStateException("Invalid for conditions: ($conditionsLine)")
            }

        val preExecuteAction = conditions.emptySafeAction(0) {
            LineParser.parse(conditions[0])
                ?: throw IllegalStateException("Invalid pre execute action inside for expression: ${conditions[0]}")
        }

        val loopCondition = conditions.emptySafeAction(1) {
            LineParser.parse(conditions[1])
                ?: throw IllegalStateException("Invalid loop condition inside for expression: ${conditions[1]}")
        }

        val iterationEndAction = conditions.emptySafeAction(2) {
            LineParser.parse(conditions[2])
                ?: throw IllegalStateException("Invalid iteration end action inside for expression: ${conditions[2]}")
        }

        val iterationAction = BlockParser.parse(iterationCode)

        return ForExpression(
            preExecuteAction,
            loopCondition,
            iterationEndAction,
            iterationAction
        )
    }

    private inline fun <reified T> List<String>.emptySafeAction(index: Int, action: () -> T): T? {
        return if (this[index].isNotEmpty()) action()
        else null
    }
}