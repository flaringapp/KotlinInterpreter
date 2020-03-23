package interpreter.expression.terminal

import interpreter.expression.IBooleanExpression

class BoolLiteral(
    value: Boolean
) : LiteralExpression<Boolean>(value), IBooleanExpression