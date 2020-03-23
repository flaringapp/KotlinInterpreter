package interpreter.expression.nonTerminal.loop

import interpreter.Context
import interpreter.expression.IActionExpression
import interpreter.expression.IBooleanExpression
import interpreter.expression.IExpression
import interpreter.expression.nonTerminal.variable.AssignExpression
import interpreter.expression.nonTerminal.INonTerminalExpression
import interpreter.statement.IStatement

class ForExpression(
    private val assignExpression: AssignExpression,
    private val loopCondition: IBooleanExpression,
    private val iterationEndAction: IExpression,
    private val iterationAction: IStatement
) : INonTerminalExpression, IActionExpression {

    override fun execute(context: Context) {
        assignExpression.execute(context)

        while (loopCondition.execute(context)) {
            iterationAction.execute(context)
            iterationEndAction.execute(context)
        }
    }

    override fun toString(): String {
        return "for($assignExpression | $loopCondition | $iterationEndAction)"
    }
}