package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.util

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.IActionExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.UnaryExpression

class PrintExpression : UnaryExpression(), IActionExpression {

    override fun execute(context: IntContext) {
        context.requestPrint(expression.execute(context))
    }

    override fun getData() = "print"
}