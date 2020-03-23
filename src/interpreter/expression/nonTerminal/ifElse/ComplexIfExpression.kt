package interpreter.expression.nonTerminal.ifElse

import interpreter.Context
import interpreter.expression.IActionExpression
import interpreter.expression.nonTerminal.INonTerminalExpression
import interpreter.statement.IStatement

class ComplexIfExpression(
    private val ifExpressions: List<IIfExpression>,
    private val elseStatement: IStatement
) : INonTerminalExpression, IActionExpression {

    override fun execute(context: Context) {
        ifExpressions.forEach { expression ->
            if (expression.condition.execute(context)) {
                expression.statement.execute(context)
                return
            }
        }
        
        elseStatement.execute(context)
    }
}