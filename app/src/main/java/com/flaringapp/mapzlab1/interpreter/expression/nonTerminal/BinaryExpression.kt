package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal

import com.flaringapp.mapzlab1.interpreter.expression.IExpression

abstract class BinaryExpression : INonTerminalExpression {

    lateinit var leftExpression: IExpression
    lateinit var rightExpression: IExpression

    fun hasLeftExpression() = ::leftExpression.isInitialized
    fun hasRightExpression() = ::rightExpression.isInitialized

    final override fun childNodes() = listOf(leftExpression, rightExpression)
}

fun <T : BinaryExpression> T.init(leftExpression: IExpression, rightExpression: IExpression) = apply {
    this.leftExpression = leftExpression
    this.rightExpression = rightExpression
}