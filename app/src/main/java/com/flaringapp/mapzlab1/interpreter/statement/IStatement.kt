package com.flaringapp.mapzlab1.interpreter.statement

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.treeview.ISplitNodeData

interface IStatement: ISplitNodeData {
    fun execute(context: Context): Any?

    fun executeNonNull(context: Context) : Any {
        return execute(context) ?: throw IllegalStateException("$this can not be null")
    }

    override fun childNodes(): List<ISplitNodeData> = emptyList()
}