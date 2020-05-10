package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.variable

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.UnaryExpression
import com.flaringapp.mapzlab1.interpreter.expression.terminal.VariableExpression
import com.flaringapp.mapzlab1.interpreter.utils.safeCast

class CreateVariableExpression : UnaryExpression() {

    override fun execute(context: Context): VariableExpression {
        val variable = expression.safeCast(VariableExpression::class)
                ?: throw IllegalStateException("$expression is not variable in $this")
        context.addVariable(variable.name)
        return variable
    }

    override fun toString(): String {
        return "var $expression"
    }

    override fun getData() = "var"
}