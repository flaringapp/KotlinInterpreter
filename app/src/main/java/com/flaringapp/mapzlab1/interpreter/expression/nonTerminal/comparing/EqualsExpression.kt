package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.comparing

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.BinaryExpression

class EqualsExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        val leftValue = leftExpression.execute(context)
        val rightValue = rightExpression.execute(context)

        return leftValue == rightValue
    }

    override fun toString(): String {
        return "$leftExpression == $rightExpression"
    }
}