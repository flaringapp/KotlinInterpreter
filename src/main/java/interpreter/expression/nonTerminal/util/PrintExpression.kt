package interpreter.expression.nonTerminal.util

import interpreter.Context
import interpreter.expression.IActionExpression
import interpreter.expression.nonTerminal.UnaryExpression

class PrintExpression : UnaryExpression(), IActionExpression {

    override fun execute(context: Context) {
        println(expression.execute(context))
    }
}