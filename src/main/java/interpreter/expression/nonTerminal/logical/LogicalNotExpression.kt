package interpreter.expression.nonTerminal.logical

import interpreter.Context
import interpreter.expression.IBooleanExpression
import interpreter.expression.nonTerminal.UnaryExpression
import interpreter.utils.typedExecute

class LogicalNotExpression : UnaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        return !expression.typedExecute<Boolean>(context)
    }

    override fun toString(): String {
        return "!$expression"
    }
}