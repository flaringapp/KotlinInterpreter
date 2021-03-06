package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.logical

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.BinaryExpression
import com.flaringapp.mapzlab1.interpreter.utils.typedExecute

class LogicalAndExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: IntContext): Boolean {
        val leftValue: Boolean = leftExpression.typedExecute(context)
        val rightValue: Boolean = rightExpression.typedExecute(context)
        return leftValue && rightValue
    }

    override fun toString(): String {
        return "$leftExpression && $rightExpression"
    }

    override fun getData() = "&&"
}