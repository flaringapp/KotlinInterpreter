package interpreter.expression.nonTerminal.variable

import interpreter.Context
import interpreter.expression.nonTerminal.UnaryExpression
import interpreter.expression.terminal.VariableExpression
import interpreter.utils.castOrTypedExecute

class CreateVariableExpression : UnaryExpression() {

    override fun execute(context: Context): VariableExpression {
        val variable: VariableExpression = expression.castOrTypedExecute(context)
        context.addVariable(variable.name)
        return variable
    }
}