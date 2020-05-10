package com.flaringapp.mapzlab1.interpreter.expression.terminal

import com.flaringapp.mapzlab1.interpreter.expression.IStringExpression

class StringLiteral(
    value: String
) : LiteralExpression<String>(value), IStringExpression