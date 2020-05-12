package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.IExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement
import com.flaringapp.mapzlab1.interpreter.utils.typedExecute

class IfExpression(
    override val condition: IExpression,
    override val statement: IStatement
) : IIfExpression {

    override fun execute(context: IntContext) {
        if (condition.typedExecute(context)) statement.execute(context)
    }

    override fun getData() = "if [cond;stat]"

    override fun childNodes() = listOf(condition, statement)
}