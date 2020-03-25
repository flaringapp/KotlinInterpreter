package interpreter.parser

import interpreter.expression.IExpression
import interpreter.parser.analyzer.CodeAnalyzer
import interpreter.parser.analyzer.ExpressionType.*
import interpreter.statement.ComplexStatement
import interpreter.statement.IStatement

object BlockParser {

    fun parse(blockCode: Code): IStatement {
        val analyzer = CodeAnalyzer(blockCode)

        val trees = mutableListOf<IExpression>()

        while (analyzer.isNotEmpty()) {
            trees += analyzer.getNextBlock().run {
                when (type) {
                    LINE -> LineParser.parse(code.first())
                    FOR -> ForParser.parse(code)
                    WHILE -> WhileParser.parse(code)
                    DO_WHILE -> DoWhileParser.parse(code)
                    else -> throw IllegalStateException("Unsupported block type $type")
                }
            } ?: continue
        }

        return ComplexStatement(trees)
    }
}