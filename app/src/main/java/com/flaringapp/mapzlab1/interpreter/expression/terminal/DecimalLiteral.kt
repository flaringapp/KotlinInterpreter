package com.flaringapp.mapzlab1.interpreter.expression.terminal

import com.flaringapp.mapzlab1.interpreter.expression.IDecimalExpression

class DecimalLiteral(
    value: Double
): LiteralExpression<Double>(value), IDecimalExpression