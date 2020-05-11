package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.logical

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.UnaryExpression
import com.flaringapp.mapzlab1.interpreter.utils.typedExecute

class LogicalNotExpression : UnaryExpression(), IBooleanExpression {

    override fun execute(context: IntContext): Boolean {
        return !expression.typedExecute<Boolean>(context)
    }

    override fun toString(): String {
        return "!$expression"
    }

    override fun getData() = "!"
}