package com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.arithmetic

import com.flaringapp.mapzlab1.interpreter.Context
import com.flaringapp.mapzlab1.interpreter.expression.nonTerminal.BinaryExpression
import com.flaringapp.mapzlab1.interpreter.expression.terminal.IValueExpression

class SubtractExpression : BinaryExpression(), IValueExpression {

    override fun execute(context: Context): Any {
        val leftValue = leftExpression.executeNonNull(context)
        val rightValue = rightExpression.executeNonNull(context)

        return (leftValue - rightValue)
            ?: throw IllegalArgumentException("Can't subtract \"$leftValue\" on \"$rightValue\"")
    }
}

@Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
private operator fun <T> Any.minus(other: Any): T? {
    val value = when (this) {
        is Int -> when (other) {
            is Int -> this - other
            is Long -> this - other
            is Float -> this - other
            is Double -> this - other
            else -> null
        }
        is Long -> when (other) {
            is Int -> this - other
            is Long -> this - other
            is Float -> this - other
            is Double -> this - other
            else -> null
        }
        is Float -> when (other) {
            is Int -> this - other
            is Long -> this - other
            is Float -> this - other
            is Double -> this - other
            else -> null
        }
        is Double -> when (other) {
            is Int -> this - other
            is Long -> this - other
            is Float -> this - other
            is Double -> this - other
            else -> null
        }
        else -> null
    }

    return value as? T
}
