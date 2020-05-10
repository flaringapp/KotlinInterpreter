package com.flaringapp.mapzlab1.interpreter

import com.flaringapp.mapzlab1.interpreter.parser.CodeParser
import com.flaringapp.mapzlab1.interpreter.statement.ComplexStatement

object Client {

    fun run(code: List<String>): ComplexStatement {
        val parsedCode = CodeParser.parse(code)

        parsedCode.execute(Context)

        return parsedCode
    }

}