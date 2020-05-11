package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement

class IfElseExpression(
    override val condition: IBooleanExpression,
    override val statement: IStatement,
    private val elseStatement: IStatement
): IIfExpression {

    override fun execute(context: IntContext) {
        if (condition.execute(context)) statement.execute(context)
        else elseStatement.execute(context)
    }

    override fun getData() = "if else [cond;stat;elseStat]"

    override fun childNodes() = listOf(condition, statement, elseStatement)
}