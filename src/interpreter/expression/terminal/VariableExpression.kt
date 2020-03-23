package interpreter.expression.terminal

import interpreter.Context

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