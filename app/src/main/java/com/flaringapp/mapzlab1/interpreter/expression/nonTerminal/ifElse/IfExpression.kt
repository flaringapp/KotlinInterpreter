package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement

class IfExpression(
    override val condition: IBooleanExpression,
    override val statement: IStatement
) : IIfExpression {

    override fun execute(context: IntContext) {
        if (condition.execute(context)) statement.execute(context)
    }

    override fun getData() = "if [cond;stat]"

    override fun childNodes() = listOf(condition, statement)
}