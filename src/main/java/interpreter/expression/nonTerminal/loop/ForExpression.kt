package interpreter.expression.nonTerminal.loop

import interpreter.Context
import interpreter.expression.IActionExpression
import interpreter.expression.IBooleanExpression
import interpreter.expression.IExpression
import interpreter.expression.nonTerminal.INonTerminalExpression
import interpreter.expression.terminal.VariableExpression
import interpreter.statement.IStatement
import interpreter.utils.safeCast

class ForExpression(
    private val preExecuteAction: IExpression?,
    private val loopCondition: IExpression?,
    private val iterationEndAction: IExpression?,
    private val iterationAction: IStatement
) : INonTerminalExpression, IActionExpression {

    override fun execute(context: Context) {
        preExecuteAction?.execute(context)

        val loopConditionAction: () -> Boolean =
            loopCondition?.let { condition ->
                condition.safeCast(IBooleanExpression::class)?.let { { it.execute(context) } }
                    ?: condition.safeCast(VariableExpression::class)?.let {
                        {
                            it.execute(context) as? Boolean
                                ?: throw IllegalStateException("Variable ${it.name} should be boolean")
                        }
                    } ?: throw IllegalStateException("$loopCondition should be a boolean expression")
            } ?: { true }

        while (loopConditionAction()) {
            iterationAction.execute(context)
            iterationEndAction?.execute(context)
        }
    }

    override fun toString(): String {
        return "for($preExecuteAction | $loopCondition | $iterationEndAction)"
    }
}