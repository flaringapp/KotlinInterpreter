package interpreter.parser

import interpreter.expression.IExpression
import interpreter.parser.utils.DO
import interpreter.parser.utils.FOR
import interpreter.parser.utils.WHILE
import interpreter.parser.utils.indicesOfFirstStatement
import interpreter.statement.ComplexStatement
import interpreter.statement.IStatement

object BlockParser {

    fun parse(code: Code): IStatement {
        val trees = mutableListOf<IExpression>()

        var i = 0
        while (i < code.size) {
            val line = code[i]

            when {
                line.startsWith(FOR) -> {
                    val forSize = code.subList(i, code.size).forStatementSize()
                    trees += ForParser.parse(code.subList(i, i + forSize))
                    i += forSize - 1
                }
                line.startsWith(DO) -> {
                    val doSize = code.subList(i, code.size).doStatementSize()
                    trees += DoParser.parse(code.subList(i, i + doSize))
                    i += doSize - 1
                }
                line.startsWith(WHILE) -> {
                    val whileSize = code.subList(i, code.size).whileStatementSize()
                    trees += WhileParser.parse(code.subList(i, i + whileSize))
                    i += whileSize - 1
                }
                else -> {
                    LineParser.parse(line, isDirty = true)
                        ?.let { trees += it }
                }
            }

            i++
        }

        return ComplexStatement(trees)
    }

    private fun Code.forStatementSize(): Int {
        return indicesOfFirstStatement().second + 1
    }

    private fun Code.doStatementSize(): Int {
        return indicesOfFirstStatement().second + 1
    }

    private fun Code.whileStatementSize(): Int {
        return indicesOfFirstStatement().second + 1
    }
}