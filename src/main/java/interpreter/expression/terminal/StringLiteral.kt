package interpreter.expression.terminal

import interpreter.expression.IStringExpression

class StringLiteral(
    value: String
) : LiteralExpression<String>(value), IStringExpression