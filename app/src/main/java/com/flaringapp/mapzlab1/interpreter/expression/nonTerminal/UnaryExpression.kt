package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal

import com.flaringapp.mapzlab1.interpreter.expression.IExpression

abstract class UnaryExpression : INonTerminalExpression {

    lateinit var expression: IExpression

    fun hasExpression() = ::expression.isInitialized

    final override fun childNodes() = listOf(expression)
}

fun <T : UnaryExpression> T.init(expression: IExpression) = apply {
    this.expression = expression
}