package com.flaringapp.mapzlab1.interpreter.parser

import com.flaringapp.mapzlab1.interpreter.expression.IExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse.ComplexIfExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse.IfElseExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse.IfExpression
import com.flaringapp.mapzlab1.interpreter.parser.utils.*
import com.flaringapp.mapzlab1.interpreter.statement.IStatement

object IfParser {

    fun parse(code: Code): IExpression {
        var workingCode = code
        val ifExpressions = mutableListOf<Pair<IExpression, IStatement>>()
        var elseExpression: IStatement? = null

        while (workingCode.isNotEmpty()) {
            val statementIndices = workingCode.indicesOfFirstStatement()

            val statementCode = workingCode.codeInsideStatement()
            val statement = BlockParser.parse(statementCode)

            val ifConditionLine = workingCode.asLineBeforeStatement()

            val isElse = ifConditionLine.startsWith(ELSE) &&
                    !ifConditionLine.startsWith(ELSE_IF)

            val condition = if (isElse) null
            else {
                val conditionLine = ifConditionLine.substring(
                    ifConditionLine.indexOf(BRACKET_START) + 1,
                    ifConditionLine.lastIndexOf(BRACKET_END)
                ).trimSpaces()

                LineParser.parse(conditionLine)
            }

            if (condition == null) elseExpression = statement
            else ifExpressions += condition to statement

            if (statementIndices.second + 1 == workingCode.size) break
            workingCode = workingCode.subList(statementIndices.second + 1, workingCode.size)
        }

        if (ifExpressions.isEmpty())
            throw IllegalStateException("There are not if expression in if block!")


        return if (ifExpressions.size == 1) {
            val data = ifExpressions.first()
            if (elseExpression == null) IfExpression(data.first, data.second)
            else IfElseExpression(data.first, data.second, elseExpression)
        } else ComplexIfExpression(
            ifExpressions.map {
                IfExpression(it.first, it.second)
            },
            elseExpression
        )
    }

}