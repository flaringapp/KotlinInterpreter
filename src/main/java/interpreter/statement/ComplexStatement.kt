package interpreter.statement

import interpreter.Context

class ComplexStatement(
    private val statements: List<IStatement>
) : IStatement {

    override fun execute(context: Context) {
        statements.forEach { it.execute(context) }
    }

    companion object {
        fun empty() = ComplexStatement(emptyList())
    }
}