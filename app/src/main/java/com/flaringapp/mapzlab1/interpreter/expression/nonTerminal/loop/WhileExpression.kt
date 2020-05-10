package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.IActionExpression
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.IExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.INonTerminalExpression
import com.flaringapp.mapzlab1.interpreter.expression.terminal.VariableExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement
import com.flaringapp.mapzlab1.interpreter.utils.safeCast
import com.flaringapp.treeview.ISplitNodeData

class WhileExpression(
    val loopCondition: IExpression,
    val iterationAction: IStatement
) : INonTerminalExpression, IActionExpression {

    override fun execute(context: Context) {
        val loopConditionAction: () -> Boolean =
            loopCondition.safeCast(IBooleanExpression::class)?.let { { it.execute(context) } }
                ?: loopCondition.safeCast(VariableExpression::class)?.let {
                    {
                        it.execute(context) as? Boolean
                            ?: throw IllegalStateException("Variable ${it.name} should be boolean")
                    }
                } ?: throw IllegalStateException("$loopCondition should be a boolean expression")

        while (loopConditionAction()) {
            iterationAction.execute(context)
        }
    }

    override fun toString(): String {
        return "while $loopCondition"
    }

    override fun getData() = "for [cond;iter]"

    override fun childNodes() = listOf(loopCondition, iterationAction)
}