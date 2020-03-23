package interpreter.expression.terminal

import interpreter.expression.IIntExpression

class IntLiteral(
    value: Int
) : LiteralExpression<Int>(value), IIntExpression