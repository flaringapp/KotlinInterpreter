package com.flaringapp.mapzlab1.interpreter.statement

import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.treeview.ISplitNodeData

interface IStatement: ISplitNodeData {
    fun execute(context: IntContext): Any?

    fun executeNonNull(context: IntContext) : Any {
        return execute(context) ?: throw IllegalStateException("$this can not be null")
    }

    override fun childNodes(): List<ISplitNodeData> = emptyList()
}