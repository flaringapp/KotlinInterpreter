package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.variable

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.ITypedValueExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.BinaryExpression
import com.flaringapp.mapzlab1.interpreter.expression.terminal.VariableExpression
import com.flaringapp.mapzlab1.interpreter.utils.castOrTypedExecute
import com.flaringapp.mapzlab1.interpreter.utils.castedExecute

class AssignExpression : BinaryExpression() {

    override fun execute(context: IntContext): Any? {
        val variable: VariableExpression = leftExpression.castOrTypedExecute(context)

        val value: Any? = rightExpression.castedExecute(context, ITypedValueExpression::class)
        context.assignVariableValue(variable.name, value)
        return value
    }

    override fun toString(): String {
        return "$leftExpression = $rightExpression"
    }

    override fun getData() = "="
}