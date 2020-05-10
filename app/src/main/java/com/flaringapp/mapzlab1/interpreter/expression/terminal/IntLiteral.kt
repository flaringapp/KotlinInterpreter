package com.flaringapp.mapzlab1.interpreter.expression.terminal

import com.flaringapp.mapzlab1.interpreter.expression.IIntExpression

class IntLiteral(
    value: Int
) : LiteralExpression<Int>(value), IIntExpression