package interpreter.expression

import interpreter.Context

interface ITypedValueExpression<T>: IExpression {

    override fun execute(context: Context): T

}