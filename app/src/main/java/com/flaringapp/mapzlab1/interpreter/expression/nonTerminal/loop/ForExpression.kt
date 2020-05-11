package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.IActionExpression
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.IExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.INonTerminalExpression
import com.flaringapp.mapzlab1.interpreter.expression.terminal.StringLiteral
import com.flaringapp.mapzlab1.interpreter.expression.terminal.VariableExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement
import com.flaringapp.mapzlab1.interpreter.utils.safeCast

class ForExpression(
    val preExecuteAction: IExpression?,
    val loopCondition: IExpression?,
    val iterationEndAction: IExpression?,
    val iterationAction: IStatement
) : INonTerminalExpression, IActionExpression {

    override fun execute(context: IntContext) {
        preExecuteAction?.execute(context)

        val loopConditionAction = loopCondition?.let { condition ->
            condition.safeCast(IBooleanExpression::class)?.let { { it.execute(context) } }
                ?: condition.safeCast(VariableExpression::class)?.let {
                    {
                        it.execute(context) as? Boolean
                            ?: throw IllegalStateException("Variable ${it.name} should be boolean")
                    }
                } ?: throw IllegalStateException("$loopCondition should be a boolean expression")
        } ?: { true }
        val a = loopCondition?.let { condition ->
            condition.safeCast(IBooleanExpression::class)?.let { { it.execute(context) } }
                ?: condition.safeCast(VariableExpression::class)?.let {
                    {
                        it.execute(context) as? Boolean
                            ?: throw IllegalStateException("Variable ${it.name} should be boolean")
                    }
                } ?: throw IllegalStateException("$loopCondition should be a boolean expression")
        } ?: { true }

        while (loopConditionAction()) {
            iterationAction.execute(context)
            iterationEndAction?.execute(context)
        }
    }

    override fun toString(): String {
        return "for($preExecuteAction | $loopCondition | $iterationEndAction)"
    }

    override fun getData() = "for [pre;cond;iEnd;iter]"

    override fun childNodes() =
        listOf(preExecuteAction, loopCondition, iterationEndAction, iterationAction)
            .map { it ?: StringLiteral("empty") }
}