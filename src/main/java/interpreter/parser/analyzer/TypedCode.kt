package interpreter.parser.analyzer

import interpreter.parser.Code

data class TypedCode(
    val code: Code,
    val type: ExpressionType
)

fun Code.typed(type: ExpressionType) = TypedCode(toList(), type)