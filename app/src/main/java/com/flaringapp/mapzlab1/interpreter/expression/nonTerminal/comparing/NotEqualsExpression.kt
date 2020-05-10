package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.comparing

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.BinaryExpression

class NotEqualsExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        val leftValue = leftExpression.executeNonNull(context)
        val rightValue = rightExpression.executeNonNull(context)

        return leftValue != rightValue
    }

    override fun toString(): String {
        return "$leftExpression != $rightExpression"
    }
}