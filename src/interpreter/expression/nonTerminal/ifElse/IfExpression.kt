package interpreter.expression.nonTerminal.ifElse

import interpreter.Context
import interpreter.expression.IBooleanExpression
import interpreter.statement.IStatement

class IfExpression(
    override val condition: IBooleanExpression,
    override val statement: IStatement
) : IIfExpression {

    override fun execute(context: Context) {
        if (condition.execute(context)) statement.execute(context)
    }
}