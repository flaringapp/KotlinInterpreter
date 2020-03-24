package interpreter.expression.nonTerminal.loop

import interpreter.Context
import interpreter.expression.IActionExpression
import interpreter.expression.IBooleanExpression
import interpreter.expression.IExpression
import interpreter.expression.nonTerminal.INonTerminalExpression
import interpreter.statement.IStatement
import interpreter.utils.castedRun

class DoExpression(
    private val iterationAction: IStatement,
    private val loopCondition: IExpression
) : INonTerminalExpression, IActionExpression {
    override fun execute(context: Context) {
        loopCondition.castedRun(IBooleanExpression::class) {
            do {
                iterationAction.execute(context)
            } while (it.execute(context))
        }
    }
}