package interpreter.expression.nonTerminal.loop

import interpreter.Context
import interpreter.expression.IActionExpression
import interpreter.expression.IBooleanExpression
import interpreter.expression.IExpression
import interpreter.expression.nonTerminal.INonTerminalExpression
import interpreter.expression.terminal.VariableExpression
import interpreter.statement.IStatement
import interpreter.utils.safeCast

class WhileExpression(
    private val loopCondition: IExpression,
    private val iterationAction: IStatement
) : INonTerminalExpression, IActionExpression {

    override fun execute(context: Context) {
        val loopConditionAction: () -> Boolean =
            loopCondition.safeCast(IBooleanExpression::class)?.let { { it.execute(context) } }
                ?: loopCondition.safeCast(VariableExpression::class)?.let {
                    {
                        it.execute(context) as? Boolean
                            ?: throw IllegalStateException("Variable ${it.name} should be boolean")
                    }
                } ?: throw IllegalStateException("$loopCondition should be a boolean expression")

        while (loopConditionAction()) {
            iterationAction.execute(context)
        }
    }

    override fun toString(): String {
        return "while $loopCondition"
    }
}