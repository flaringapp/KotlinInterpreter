package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.comparing

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.BinaryExpression

class MoreEqualsExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        val moreExpression = MoreExpression().also {
            it.leftExpression = leftExpression
            it.rightExpression = rightExpression
        }
        val equalsExpression = EqualsExpression().also {
            it.leftExpression = leftExpression
            it.rightExpression = rightExpression
        }
        return moreExpression.execute(context) && equalsExpression.execute(context)
    }
}