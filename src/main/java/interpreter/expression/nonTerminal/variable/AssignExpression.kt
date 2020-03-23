package interpreter.expression.nonTerminal.variable

import interpreter.Context
import interpreter.expression.ITypedValueExpression
import interpreter.expression.nonTerminal.BinaryExpression
import interpreter.expression.terminal.VariableExpression
import interpreter.utils.castOrTypedExecute
import interpreter.utils.castedExecute

class AssignExpression : BinaryExpression() {

    override fun execute(context: Context): Any? {
        val variable: VariableExpression = leftExpression.castOrTypedExecute(context)

        val value: Any? = rightExpression.castedExecute(context, ITypedValueExpression::class)
        context.assignVariableValue(variable.name, value)
        return value
    }

    override fun toString(): String {
        return "$leftExpression = $rightExpression"
    }
}