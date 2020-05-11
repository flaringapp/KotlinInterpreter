package com.flaringapp.mapzlab1.interpreter.parser

import com.flaringapp.mapzlab1.interpreter.parser.inliner.inline
import com.flaringapp.mapzlab1.interpreter.statement.ComplexStatement

typealias Code = List<String>

object CodeParser {

    fun parse(code: Code): ComplexStatement {
        val inlinedCode = code.inline()
        return BlockParser.parse(inlinedCode)
    }
}