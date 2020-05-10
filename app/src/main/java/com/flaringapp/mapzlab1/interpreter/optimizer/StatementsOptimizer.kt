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
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.variable.CreateVariableExpression
import com.flaringapp.mapzlab1.interpreter.expression.terminal.VariableExpression
import com.flaringapp.mapzlab1.interpreter.statement.ComplexStatement
import com.flaringapp.mapzlab1.interpreter.statement.IStatement
import com.flaringapp.mapzlab1.interpreter.utils.safeCastedRun

object StatementsOptimizer {

    fun ComplexStatement.optimize() = apply {
        removeUnusedVariables()
    }

    private fun ComplexStatement.removeUnusedVariables() {
        val variables = VariableSearcher.findAllVariables(this)
        VariablesOptimizer.optimizeVariables(this, variables)
        UnusedVariablesCleaner.cleanUnusedVariables(this, variables)
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

    private fun IExpression.isVariable(name: String) =
        this is VariableExpression && this.name == name

    private fun IExpression.isCreateVariable(name: String): Boolean {
        return this is CreateVariableExpression &&
                expression.safeCastedRun(VariableExpression::class) { it.name == name }
                ?: false
    }

    private object VariableSearcher {

        fun findAllVariables(statement: ComplexStatement): List<String> {
            val variables = mutableListOf<String>()
            statement.findAllVariables(variables)
            return variables
        }

        private fun ComplexStatement.findAllVariables(variables: MutableList<String>) {
            statements.forEach { it.findAllVariables(variables) }
        }

        private fun IStatement.findAllVariables(variables: MutableList<String>) {
            when (this) {
                is IExpression -> findAllExpressionVariables(variables)
                is IIfExpression -> findAllIfVariables(variables)
                is ComplexIfExpression -> findAllComplexIfVariables(variables)
                is ForExpression -> findAllForVariables(variables)
                is DoExpression -> findAllDoVariables(variables)
                is WhileExpression -> findAllWhileVariables(variables)
                is ComplexStatement -> findAllVariables(variables)
            }
        }

        private fun IExpression.findAllExpressionVariables(variables: MutableList<String>) {
            when (this) {
                is CreateVariableExpression -> {
                    expression.safeCastedRun(VariableExpression::class) {
                        if (!variables.contains(it.name)) variables.add(it.name)
                    }
                }
                is UnaryExpression -> expression.findAllExpressionVariables(variables)
                is BinaryExpression -> {
                    leftExpression.findAllExpressionVariables(variables)
                    rightExpression.findAllExpressionVariables(variables)
                }
            }
        }

        private fun IIfExpression.findAllIfVariables(variables: MutableList<String>) {
            condition.findAllExpressionVariables(variables)
            statement.findAllVariables(variables)
        }

        private fun ComplexIfExpression.findAllComplexIfVariables(variables: MutableList<String>) {
            ifExpressions.forEach { it.findAllIfVariables(variables) }
            elseStatement.findAllVariables(variables)
        }

        private fun ForExpression.findAllForVariables(variables: MutableList<String>) {
            preExecuteAction?.findAllVariables(variables)
            loopCondition?.findAllVariables(variables)
            iterationEndAction?.findAllVariables(variables)
            iterationAction.findAllVariables(variables)
        }

        private fun DoExpression.findAllDoVariables(variables: MutableList<String>) {
            iterationAction.findAllVariables(variables)
            loopCondition.findAllExpressionVariables(variables)
        }

        private fun WhileExpression.findAllWhileVariables(variables: MutableList<String>) {
            loopCondition.findAllExpressionVariables(variables)
            iterationAction.findAllVariables(variables)
        }
    }

    object VariablesOptimizer {

        fun optimizeVariables(statement: ComplexStatement, variables: List<String>) {
            variables.forEach { statement.optimizeVariable(VariableData(it, null)) }
        }

        private fun ComplexStatement.optimizeVariable(data: VariableData) {
            statements.forEach {
                it.optimizeVariable(data)
            }
        }

        private fun IStatement.optimizeVariable(data: VariableData) {
            when (this) {
                is IExpression -> optimizeExpressionVariable(data)
                is IIfExpression -> optimizeIfVariable(data)
                is ComplexIfExpression -> optimizeComplexIfVariable(data)
                is ForExpression -> optimizeForVariable(data)
                is DoExpression -> optimizeDoVariable(data)
                is WhileExpression -> optimizeWhileVariable(data)
                is ComplexStatement -> optimizeVariable(data)
            }
        }

        private fun IExpression.optimizeExpressionVariable(data: VariableData) {
            when (this) {
                is UnaryExpression -> {
                    if (this is CreateVariableExpression) return
                    if (expression.isVariable(data.variable)) {
                        data.value?.let { expression = it }
                    } else expression.optimizeExpressionVariable(data)
                }
                is BinaryExpression -> {
                    if (rightExpression.isVariable(data.variable)) {
                        data.value?.let { rightExpression = it }
                    } else rightExpression.optimizeExpressionVariable(data)

                    if (leftExpression.isVariable(data.variable)) {
                        if (this is AssignExpression) {
                            data.value = rightExpression
                        } else {
                            data.value?.let { leftExpression = it }
                        }
                    } else if (leftExpression is CreateVariableExpression) {
                        (leftExpression as CreateVariableExpression).expression
                            .safeCastedRun(VariableExpression::class) {
                                if (it.name == data.variable) data.value = rightExpression
                            }
                    } else leftExpression.optimizeExpressionVariable(data)
                }
            }
        }

        private fun IExpression.isVariable(name: String): Boolean {
            return (this as? VariableExpression)?.let {
                it.name == name
            } ?: false
        }

        private fun IIfExpression.optimizeIfVariable(data: VariableData) {
            condition.optimizeVariable(data)
            statement.optimizeVariable(data)
        }

        private fun ComplexIfExpression.optimizeComplexIfVariable(data: VariableData) {
            ifExpressions.forEach { it.optimizeIfVariable(data) }
            elseStatement.optimizeVariable(data)
        }

        private fun ForExpression.optimizeForVariable(data: VariableData) {
            if (isForVariableUsed(data.variable))
                preExecuteAction?.optimizeVariable(data)
            loopCondition?.optimizeVariable(data)
            iterationEndAction?.optimizeVariable(data)
            iterationAction.optimizeVariable(data)
        }

        private fun DoExpression.optimizeDoVariable(data: VariableData) {
            iterationAction.optimizeVariable(data)
            loopCondition.optimizeExpressionVariable(data)
        }

        private fun WhileExpression.optimizeWhileVariable(data: VariableData) {
            loopCondition.optimizeExpressionVariable(data)
            iterationAction.optimizeVariable(data)
        }

        private class VariableData(
            val variable: String,
            var value: IExpression?
        )
    }

    private object UnusedVariablesCleaner {

        fun cleanUnusedVariables(statement: ComplexStatement, variables: List<String>) {
            variables.forEach {
                if (!statement.isVariableUsed(it)) statement.cleanUnusedVariable(it)
            }
        }

        private fun ComplexStatement.cleanUnusedVariable(variable: String) {
            val cleanedStatements = statements.toMutableList()

            val iterator = cleanedStatements.listIterator()
            while (iterator.hasNext()) {
                if (iterator.next().shouldBeRemoved(variable)) iterator.remove()
            }

            statements = cleanedStatements.toList()
        }

        private fun IStatement.shouldBeRemoved(variable: String): Boolean {
            return when (this) {
                is IExpression -> shouldExpressionBeRemoved(variable)
                else -> false
            }
        }

        private fun IExpression.shouldExpressionBeRemoved(variable: String): Boolean {
            if (this.isCreateVariable(variable)) return true

            if (this is AssignExpression) {
                if (
                    leftExpression.isCreateVariable(variable) ||
                    leftExpression.isVariable(variable)
                ) return true
            }

            return false
        }
    }
}