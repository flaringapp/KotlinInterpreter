package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.IActionExpression
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.IExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.INonTerminalExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement
import com.flaringapp.mapzlab1.interpreter.utils.castedRun

class DoExpression(
    val iterationAction: IStatement,
    val loopCondition: IExpression
) : INonTerminalExpression, IActionExpression {
    override fun execute(context: Context) {
        loopCondition.castedRun(IBooleanExpression::class) {
            do {
                iterationAction.execute(context)
            } while (it.execute(context))
        }
    }
}