package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.ifElse

import com.flaringapp.mapzlab1.interpreter.expression.IActionExpression
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.INonTerminalExpression
import com.flaringapp.mapzlab1.interpreter.statement.IStatement

interface IIfExpression : INonTerminalExpression, IActionExpression {
    val condition: IBooleanExpression
    val statement: IStatement
}