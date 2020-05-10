package com.flaringapp.mapzlab1.interpreter.parser

import com.flaringapp.mapzlab1.interpreter.expression.IExpression
import com.flaringapp.mapzlab1.interpreter.parser.analyzer.CodeAnalyzer
import com.flaringapp.mapzlab1.interpreter.parser.analyzer.ExpressionType.*
import com.flaringapp.mapzlab1.interpreter.statement.ComplexStatement

object BlockParser {

    fun parse(blockCode: Code): ComplexStatement {
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