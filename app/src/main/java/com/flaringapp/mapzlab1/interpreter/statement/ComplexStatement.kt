package com.flaringapp.mapzlab1.interpreter.statement

import com.flaringapp.mapzlab1.interpreter.Context

class ComplexStatement(
    val statements: List<IStatement>
) : IStatement {

    override fun execute(context: Context) {
        statements.forEach { it.execute(context) }
    }

    companion object {
        fun empty() = ComplexStatement(emptyList())
    }
}