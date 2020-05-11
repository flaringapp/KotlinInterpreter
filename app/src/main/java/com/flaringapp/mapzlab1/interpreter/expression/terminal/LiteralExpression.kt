package com.flaringapp.mapzlab1.interpreter.expression.terminal

import com.flaringapp.mapzlab1.interpreter.IntContext

abstract class LiteralExpression<T>(
    private val value: T
) : ITerminalExpression {

    override fun execute(context: IntContext): T {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }

    override fun getData() = value.toString()
}