package com.flaringapp.mapzlab1.interpreter.parser.analyzer

import com.flaringapp.mapzlab1.interpreter.parser.Code

data class TypedCode(
    val code: Code,
    val type: ExpressionType
)

fun Code.typed(type: ExpressionType) = TypedCode(toList(), type)