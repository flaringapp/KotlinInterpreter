package interpreter.expression.terminal

import interpreter.Context

abstract class LiteralExpression<T>(
    private val value: T
) : ITerminalExpression {

    override fun execute(context: Context): T {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }
}