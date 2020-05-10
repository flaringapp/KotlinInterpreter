package com.flaringapp.mapzlab1.interpreter.expression.terminal

import com.flaringapp.mapzlab1.interpreter.Context

class VariableExpression(
    val name: String
) : ITerminalExpression, IValueExpression {

    override fun execute(context: Context): Any {
        return context.getVariable(name)
    }

    override fun toString(): String {
        return name
    }

}