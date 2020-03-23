package interpreter.expression.nonTerminal.loop

import interpreter.Context
import interpreter.expression.IActionExpression
import interpreter.expression.IBooleanExpression
import interpreter.expression.IExpression
import interpreter.expression.nonTerminal.INonTerminalExpression
import interpreter.statement.IStatement

class WhileExpression(
    private val loopCondition: IBooleanExpression,
    private val iterationAction: IStatement
) : INonTerminalExpression, IActionExpression {

    override fun execute(context: Context) {
        while (loopCondition.execute(context)) {
            iterationAction.execute(context)
        }
    }

    override fun toString(): String {
        return "while $loopCondition"
    }
}