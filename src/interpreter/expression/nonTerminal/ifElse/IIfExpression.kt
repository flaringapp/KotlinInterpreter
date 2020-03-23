package interpreter.expression.nonTerminal.ifElse

import interpreter.expression.IActionExpression
import interpreter.expression.IBooleanExpression
import interpreter.expression.nonTerminal.INonTerminalExpression
import interpreter.statement.IStatement

interface IIfExpression : INonTerminalExpression, IActionExpression {
    val condition: IBooleanExpression
    val statement: IStatement
}