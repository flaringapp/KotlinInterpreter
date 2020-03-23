package interpreter.expression.nonTerminal.comparing

import interpreter.Context
import interpreter.expression.IBooleanExpression
import interpreter.expression.nonTerminal.BinaryExpression

class LessEqualsExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        val lessExpression = LessExpression().also {
            it.leftExpression = leftExpression
            it.rightExpression = rightExpression
        }
        val equalsExpression = EqualsExpression().also {
            it.leftExpression = leftExpression
            it.rightExpression = rightExpression
        }
        return lessExpression.execute(context) && equalsExpression.execute(context)
    }
}