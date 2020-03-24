package interpreter.utils

import interpreter.Context
import interpreter.expression.IExpression
import kotlin.reflect.KClass
import kotlin.reflect.full.cast
import kotlin.reflect.full.safeCast


inline fun <reified T : IExpression> IExpression.castOrTypedExecute(context: Context): T {
    return safeCast(T::class)
        ?: safeTypedExecute(context)
        ?: throw IllegalStateException(
            "Neither ${this::class.simpleName} nor it's execute result is subtype of requested type ${T::class.simpleName}"
        )
}

inline fun <reified T : IExpression> IExpression.safeCastOrTypedExecute(context: Context): T? {
    return safeCastedExecute(context, T::class)
        ?: safeTypedExecute(context)
}


inline fun <reified T : IExpression, reified A> IExpression.castedExecute(context: Context, type: KClass<T>): A {
    return castedRun(type) { it.execute(context) } as? A
        ?: throw IllegalStateException(
            "Executing of $this (${this::class.simpleName}) should return ${A::class.simpleName}"
        )
}

inline fun <reified T : IExpression, reified A> IExpression.safeCastedExecute(context: Context, type: KClass<T>): A? {
    return safeCastedRun(type) { it.execute(context) } as? A
}

/**
 * @see safeTypedAction
 * @throws IllegalStateException if cast was failed
 */
inline fun <reified T> IExpression.typedDeepExecute(context: Context): T {
    return safeTypedDeepExecute(context)
        ?: throw IllegalStateException(
            "$this (${this::class.simpleName}) should deep return ${T::class.simpleName}"
        )
}

/**
 * @see safeTypedAction
 * @return expression execute result or null if execute result cast was failed
 */
inline fun <reified T> IExpression.safeTypedDeepExecute(context: Context): T? {
    return safeTypedAction { deepExecute(context) }
}

/**
 * @see safeTypedAction
 * @throws IllegalStateException if cast was failed
 */
inline fun <reified T> IExpression.typedExecute(context: Context): T {
    return safeTypedExecute(context)
        ?: throw IllegalStateException(
            "$this (${this::class.simpleName}) should return ${T::class.simpleName}"
        )
}

/**
 * @see safeTypedAction
 * @return expression execute result or null if execute result cast was failed
 */
inline fun <reified T> IExpression.safeTypedExecute(context: Context): T? {
    return safeTypedAction { execute(context) }
}

/**
 * Runs @param action and casts to generic type
 * @return casted action result or null if cast failed
 */
inline fun <reified T> safeTypedAction(action: () -> Any?): T? {
    return action() as? T
}

/**
 * @see safeCastedRun
 * @throws IllegalStateException if cast was failed
 */
inline fun <reified T : Any, A> Any.castedRun(type: KClass<T>, action: (T) -> A): A {
    return safeCastedRun(type, action)
        ?: throw IllegalStateException(
            "$this (${this::class.simpleName}) should be ${type.simpleName}"
        )
}

/**
 * Run @param action if target is casted to @param type or throw exception
 * @return action result or null if target casted failed
 */
inline fun <reified T : Any, A> Any.safeCastedRun(type: KClass<T>, action: (T) -> A): A? {
    return safeCast(type)?.let(action)
}

fun <T : Any> Any.cast(clazz: KClass<out T>): T = clazz.cast(this)
fun <T : Any> Any.safeCast(clazz: KClass<out T>): T? = clazz.safeCast(this)

fun IExpression.deepExecute(context: Context): Any? {
    var expression: Any? = this
    while (expression is IExpression) {
        expression = expression.execute(context)
    }

    return expression
}