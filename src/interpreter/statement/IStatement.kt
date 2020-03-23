package interpreter.statement

import interpreter.Context

interface IStatement {
    fun execute(context: Context): Any?

    fun executeNonNull(context: Context) : Any {
        return execute(context) ?: throw IllegalStateException("$this can not be null")
    }
}