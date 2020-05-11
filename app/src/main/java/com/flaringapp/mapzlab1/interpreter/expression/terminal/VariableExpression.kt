package com.flaringapp.mapzlab1.interpreter.expression.terminal

import com.flaringapp.mapzlab1.interpreter.IntContext

class VariableExpression(
    val name: String
) : ITerminalExpression, IValueExpression {

    override fun execute(context: IntContext): Any {
        return context.getVariable(name)
    }

    override fun toString(): String {
        return name
    }

    override fun getData() = toString()
}