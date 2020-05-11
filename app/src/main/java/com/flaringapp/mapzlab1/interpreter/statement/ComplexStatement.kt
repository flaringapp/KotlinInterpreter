package com.flaringapp.mapzlab1.interpreter.statement

import com.flaringapp.mapzlab1.interpreter.IntContext

class ComplexStatement(
    val title: String,
    var statements: List<IStatement>
) : IStatement {

    override fun execute(context: IntContext) {
        statements.forEach { it.execute(context) }
    }

    override fun getData() = title

    override fun childNodes() = statements
}