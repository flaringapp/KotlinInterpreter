package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.comparing

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.BinaryExpression

class LessEqualsExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: IntContext): Boolean {
        val lessExpression = LessExpression().also {
            it.leftExpression = leftExpression
            it.rightExpression = rightExpression
        }
        val equalsExpression = EqualsExpression().also {
            it.leftExpression = leftExpression
            it.rightExpression = rightExpression
        }
        return lessExpression.execute(context) && equalsExpression.execute(context)
    }

    override fun getData() = "<="
}