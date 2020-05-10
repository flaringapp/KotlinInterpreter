package com.flaringapp.mapzlab1.interpreter

import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.arithmetic.AddExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.comparing.LessExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.init
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.loop.ForExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.util.PrintExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.variable.AssignExpression
import com.flaringapp.mapzlab1.interpreter.expression.terminal.IntLiteral
import com.flaringapp.mapzlab1.interpreter.expression.terminal.VariableExpression
import com.flaringapp.mapzlab1.interpreter.parser.Code
import com.flaringapp.mapzlab1.interpreter.parser.CodeParser
import com.flaringapp.mapzlab1.interpreter.statement.IStatement
import com.flaringapp.mapzlab1.interpreter.optimizer.StatementsOptimizer.optimize
import java.io.File

object Client {

    fun run() {
        val codeFile = File("raw/code.txt")

        val code: Code = codeFile.readLines()

        val parsedCode = CodeParser.parse(code)

        parsedCode.execute(Context)
    }

    fun sampleExecute() {
        val aExp = IntLiteral(1)
        val bExp = IntLiteral(2)

        val addExp = AddExpression().init(aExp, bExp)

        val cExp = IntLiteral(3)
        val varExp = VariableExpression("var")

        val assignExp = AssignExpression().init(varExp, cExp)

        val addMoreExp = AddExpression().init(addExp, varExp)

        val printExp = PrintExpression().init(addMoreExp)

        val statement = object : IStatement {
            override fun execute(context: Context) {
                assignExp.execute(Context)
                printExp.execute(Context)
            }
        }

        statement.execute(Context)
    }

    fun executeFor() {
        val zeroExp = IntLiteral(0)
        val oneExp = IntLiteral(1)
        val threeExp = IntLiteral(3)

        val iExp = VariableExpression("i")
        val assignExpression = AssignExpression().init(iExp, zeroExp)

        val lessExp = LessExpression().init(iExp, threeExp)

        val iIncrementedExp = AddExpression().init(iExp, oneExp)
        val incrementExp = AssignExpression().init(iExp, iIncrementedExp)

        val printExp = PrintExpression().init(iExp)

        val forExp = ForExpression(
            assignExpression,
            lessExp,
            incrementExp,
            printExp
        )

        forExp.execute(Context)
    }

}