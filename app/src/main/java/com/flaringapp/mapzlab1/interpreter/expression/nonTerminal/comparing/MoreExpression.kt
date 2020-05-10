package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.comparing

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.IBooleanExpression
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.BinaryExpression

class MoreExpression : BinaryExpression(), IBooleanExpression {

    override fun execute(context: Context): Boolean {
        val leftValue = leftExpression.executeNonNull(context)
        val rightValue = rightExpression.executeNonNull(context)

        return leftValue.greaterThan(rightValue)
            ?: throw IllegalArgumentException("Can't compare \"$leftValue\" with \"$rightValue\"")
    }

    override fun toString(): String {
        return "$leftExpression > $rightExpression"
    }
}

private fun Any.greaterThan(other: Any): Boolean? {
    return when (this) {
        is Int -> when (other) {
            is Int -> this - other > 0
            is Long -> this.toLong() - other > 0L
            is Float -> this.toFloat() - other > 0f
            is Double -> this.toDouble() - other > 0.0
            else -> null
        }
        is Long -> when (other) {
            is Int -> this - other.toLong() > 0L
            is Long -> this - other > 0L
            is Float -> this.toDouble() - other.toDouble() > 0.0
            is Double -> this.toDouble() - other > 0.0
            else -> null
        }
        is Float -> when (other) {
            is Int -> this - other.toFloat() > 0f
            is Long -> this.toDouble() - other.toDouble() > 0.0
            is Float -> this - other > 0f
            is Double -> this.toDouble() - other > 0.0
            else -> null
        }
        is Double -> when (other) {
            is Int -> this - other.toDouble() > 0.0
            is Long -> this - other.toDouble() > 0.0
            is Float -> this.toDouble() - other > 0.0
            is Double -> this - other > 0.0
            else -> null
        }
        else -> null
    }
}
