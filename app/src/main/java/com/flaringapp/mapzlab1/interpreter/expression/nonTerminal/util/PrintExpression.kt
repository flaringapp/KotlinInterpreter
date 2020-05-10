package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.util

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.IActionExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.UnaryExpression

class PrintExpression : UnaryExpression(), IActionExpression {

    override fun execute(context: Context) {
        println(expression.execute(context))
    }
}