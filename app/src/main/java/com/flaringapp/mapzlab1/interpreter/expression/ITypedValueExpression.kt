package com.flaringapp.mapzlab1.interpreter.expression

import com.flaringapp.mapzlab1.interpreter.Context

interface ITypedValueExpression<T>: IExpression {

    override fun execute(context: Context): T

}