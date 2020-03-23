package interpreter.expression.nonTerminal.comparing

import interpreter.Context
import interpreter.expression.IBooleanExpression
import interpreter.expression.nonTerminal.BinaryExpression

class NotEqualsExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        val leftValue = leftExpression.executeNonNull(context)
        val rightValue = rightExpression.executeNonNull(context)

        return leftValue != rightValue
    }

    override fun toString(): String {
        return "$leftExpression != $rightExpression"
    }
}