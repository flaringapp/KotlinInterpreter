package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.variable

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.UnaryExpression
import com.flaringapp.mapzlab1.interpreter.expression.terminal.VariableExpression
import com.flaringapp.mapzlab1.interpreter.utils.castOrTypedExecute

class CreateVariableExpression : UnaryExpression() {

    override fun execute(context: Context): VariableExpression {
        val variable: VariableExpression = expression.castOrTypedExecute(context)
        context.addVariable(variable.name)
        return variable
    }
}