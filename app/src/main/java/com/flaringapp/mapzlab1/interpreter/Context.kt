package com.flaringapp.mapzlab1.interpreter

object Context {

    //  Variable name <-> value
    private val variables: MutableMap<String, Any?> = mutableMapOf()

    fun addVariable(name: String) {
        if (variables.containsKey(name))
            throw IllegalStateException("Variable $name is already created")

        variables[name] = null
    }

    fun assignVariableValue(name: String, value: Any?) {
        variables[name] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getVariable(name: String): T {
        return variables[name]?.let {
            it as? T ?: throw ClassCastException(
                "Requested variable $name is of not requested type. It is \"$variables\""
            )
        } ?: throw IllegalStateException("There is no variable of name \"$name\"")
    }
}