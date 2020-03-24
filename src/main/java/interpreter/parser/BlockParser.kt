package interpreter.parser

import interpreter.statement.ComplexStatement
import interpreter.statement.IStatement

object BlockParser {

    fun parse(code: Code): IStatement {
        val trees = code.mapNotNull {
            LineParser.parse(it, isDirty = true)
        }

        return ComplexStatement(trees)
    }
}