package com.flaringapp.mapzlab1.interpreter.expression

import com.flaringapp.mapzlab1.interpreter.IntContext

interface ITypedValueExpression<T>: IExpression {

    override fun execute(context: IntContext): T

}