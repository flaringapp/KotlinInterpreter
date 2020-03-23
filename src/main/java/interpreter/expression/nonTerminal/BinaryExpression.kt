package interpreter.expression.nonTerminal

import interpreter.expression.IExpression

abstract class BinaryExpression : INonTerminalExpression {

    lateinit var leftExpression: IExpression
    lateinit var rightExpression: IExpression

    fun hasLeftExpression() = ::leftExpression.isInitialized
    fun hasRightExpression() = ::rightExpression.isInitialized
}

fun <T : BinaryExpression> T.init(leftExpression: IExpression, rightExpression: IExpression) = apply {
    this.leftExpression = leftExpression
    this.rightExpression = rightExpression
}