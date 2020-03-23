package interpreter.expression.nonTerminal.logical

import interpreter.Context
import interpreter.expression.IBooleanExpression
import interpreter.expression.nonTerminal.BinaryExpression
import interpreter.utils.typedExecute

class LogicalAndExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        val leftValue: Boolean = leftExpression.typedExecute(context)
        val rightValue: Boolean = rightExpression.typedExecute(context)
        return leftValue && rightValue
    }

    override fun toString(): String {
        return "$leftExpression && $rightExpression"
    }
}