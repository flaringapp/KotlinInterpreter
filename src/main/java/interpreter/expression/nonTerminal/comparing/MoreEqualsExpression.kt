package interpreter.expression.nonTerminal.comparing

import interpreter.Context
import interpreter.expression.IBooleanExpression
import interpreter.expression.nonTerminal.BinaryExpression

class MoreEqualsExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        val moreExpression = MoreExpression().also {
            it.leftExpression = leftExpression
            it.rightExpression = rightExpression
        }
        val equalsExpression = EqualsExpression().also {
            it.leftExpression = leftExpression
            it.rightExpression = rightExpression
        }
        return moreExpression.execute(context) && equalsExpression.execute(context)
    }
}