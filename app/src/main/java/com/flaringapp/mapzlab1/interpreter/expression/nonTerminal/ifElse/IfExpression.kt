package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement
import com.flaringapp.treeview.ISplitNodeData

class IfExpression(
    override val condition: IBooleanExpression,
    override val statement: IStatement
) : IIfExpression {

    override fun execute(context: Context) {
        if (condition.execute(context)) statement.execute(context)
    }

    override fun getData() = "if [cond;stat]"

    override fun childNodes() = listOf(condition, statement)
}