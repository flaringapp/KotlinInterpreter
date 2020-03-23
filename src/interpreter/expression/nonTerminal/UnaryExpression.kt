package interpreter.expression.nonTerminal

import interpreter.expression.IExpression

abstract class UnaryExpression : INonTerminalExpression {

    lateinit var expression: IExpression

    fun hasExpression() = ::expression.isInitialized
}

fun <T : UnaryExpression> T.init(expression: IExpression) = apply {
    this.expression = expression
}