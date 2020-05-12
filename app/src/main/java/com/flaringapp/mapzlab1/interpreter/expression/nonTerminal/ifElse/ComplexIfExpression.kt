package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.expression.IActionExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.INonTerminalExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement
import com.flaringapp.mapzlab1.interpreter.utils.typedExecute

class ComplexIfExpression(
    val ifExpressions: List<IIfExpression>,
    val elseStatement: IStatement?
) : INonTerminalExpression, IActionExpression {

    override fun execute(context: IntContext) {
        ifExpressions.forEach { expression ->
            if (expression.condition.typedExecute(context)) {
                expression.statement.execute(context)
                return
            }
        }
        
        elseStatement?.execute(context)
    }

    override fun getData() = "complex if [ifExpr[i];else]"

    override fun childNodes() = ifExpressions.let {
        if (elseStatement != null) it + elseStatement
        else it
    }
}