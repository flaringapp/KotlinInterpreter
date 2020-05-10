package com.flaringapp.mapzlab1.interpreter.expression.terminal

import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression

class BoolLiteral(
    value: Boolean
) : LiteralExpression<Boolean>(value), IBooleanExpression