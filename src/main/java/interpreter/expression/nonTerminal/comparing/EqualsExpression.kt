package interpreter.expression.nonTerminal.comparing

import interpreter.Context
import interpreter.expression.IBooleanExpression
import interpreter.expression.nonTerminal.BinaryExpression

class EqualsExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        val leftValue = leftExpression.execute(context)
        val rightValue = rightExpression.execute(context)

        return leftValue == rightValue
    }

    override fun toString(): String {
        return "$leftExpression == $rightExpression"
    }
}