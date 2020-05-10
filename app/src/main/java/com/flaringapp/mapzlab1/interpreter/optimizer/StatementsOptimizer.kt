package com.flaringapp.mapzlab1.interpreter.optimizer

import com.flaringapp.mapzlab1.interpreter.expression.IExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.BinaryExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.UnaryExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse.ComplexIfExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse.IIfExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop.DoExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop.ForExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop.WhileExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.variable.AssignExpression
import com.flaringapp.mapzlab1.interpreter.expression.terminal.VariableExpression
import com.flaringapp.mapzlab1.interpreter.statement.ComplexStatement
import com.flaringapp.mapzlab1.interpreter.statement.IStatement

object StatementsOptimizer {

    fun ComplexStatement.optimize() = apply {
        removeUnusedVariables()
    }

    private fun ComplexStatement.removeUnusedVariables() {
        //TODO search all variables
    }

    private fun ComplexStatement.isVariableUsed(name: String): Boolean {
        return statements.find { it.isVariableUsed(name) } != null
    }

    private fun IStatement.isVariableUsed(name: String): Boolean {
        return when (this) {
            is ComplexStatement -> isVariableUsed(name)
            is IIfExpression -> isIfVariableUsed(name)
            is ComplexIfExpression -> {
                ifExpressions.find { it.isIfVariableUsed(name) } != null ||
                        elseStatement.isVariableUsed(name)
            }
            is ForExpression -> isForVariableUsed(name)
            is DoExpression -> isDoVariableUsed(name)
            is WhileExpression -> isWhileVariableUsed(name)
            else -> {
                if (this is IExpression) isVariableReassignedWithOtherVariables(name)
                else false
            }
        }
    }

    private fun IIfExpression.isIfVariableUsed(name: String) = statement.isVariableUsed(name)

    private fun ForExpression.isForVariableUsed(name: String): Boolean {
        if (iterationEndAction?.isVariableUsed(name) == true) return true
        return iterationAction.isVariableUsed(name)
    }

    private fun DoExpression.isDoVariableUsed(name: String): Boolean {
        return iterationAction.isVariableUsed(name)
    }

    private fun WhileExpression.isWhileVariableUsed(name: String): Boolean {
        return iterationAction.isVariableUsed(name)
    }

    private fun IExpression.isVariableReassignedWithOtherVariables(name: String): Boolean {
        return when (this) {
            is AssignExpression -> {
                leftExpression.isVariable(name) && rightExpression.hasAnyVariable() &&
                        !rightExpression.isVariable(name)
            }
            else -> false
        }
    }

    private fun IExpression.hasAnyVariable(): Boolean {
        return when (this) {
            is VariableExpression -> true // TODO recursively check if variable is used?
            is UnaryExpression -> expression.hasAnyVariable()
            is BinaryExpression -> leftExpression.hasAnyVariable() || rightExpression.hasAnyVariable()
            //TODO if, loops
            else -> false
        }
    }

    private fun IExpression.isVariable(name: String) = this is VariableExpression && this.name == name
}