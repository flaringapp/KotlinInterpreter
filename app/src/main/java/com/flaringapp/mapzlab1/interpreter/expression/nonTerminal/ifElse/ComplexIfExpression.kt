package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.IActionExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.INonTerminalExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement
import com.flaringapp.treeview.ISplitNodeData

class ComplexIfExpression(
    val ifExpressions: List<IIfExpression>,
    val elseStatement: IStatement
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

    override fun getData() = "complex if [ifExpr[i];else]"

    override fun childNodes() = ifExpressions + elseStatement
}