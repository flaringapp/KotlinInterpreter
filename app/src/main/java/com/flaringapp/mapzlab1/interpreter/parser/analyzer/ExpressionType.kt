package com.flaringapp.mapzlab1.interpreter.parser.analyzer

enum class ExpressionType {
    FOR,
    WHILE,
    DO_WHILE,
    LINE
}

//import com.flaringapp.mapzlab1.interpreter.parser.*
//
//typealias MatchCondition = (String) -> Boolean
//
//    sealed class ExpressionType(
//        val matchCondition: MatchCondition
//) {
//    fun matches(statement: String): Boolean {
//        return statement.isNotEmpty() && matchCondition(statement.trimSpaces())
//    }
//
//    // Literals
//    object IntLiteral : ExpressionType({
//        it.isDigitsOnly()
//    })
//
//    object BoolLiteral : ExpressionType({
//        it == TRUE || it == FALSE
//    })
//
//    object StringLiteral : ExpressionType({
//        it.startsWith(QUOTE) && it.endsWith(QUOTE)
//    })
//
//    // Variables
//    object Variable : ExpressionType({
//        !it.startsWithDigit() && it.substring(1).isDigitsOrLetters()
//    })
//
//    object CreateVariable : ExpressionType({
//        it.startsWith(VAR_DECLARATION) && run {
//            val lastIndexOfKey = it.indexOf(VAR_DECLARATION) + VAR_DECLARATION.length
//            val variable = it.substring(lastIndexOfKey)
//            Variable.matches(variable)
//        }
//    })
//
//    object Assign : ExpressionType({
//        it.count { char -> char == EQUALS } == 1 && run {
//            val equalsIndex = it.indexOf(EQUALS)
//            val variable = it.substring(0, equalsIndex)
//            val value = it.substring(equalsIndex + 1)
//            Variable.matches(variable) && isValue(value)
//        }
//    })
//
//    // Complex
//    object For : ExpressionType({
//        it.trimSpaces().startsWith(FOR)
//    })
//
//    object Do : ExpressionType({
//        it.trimSpaces().startsWith(DO)
//    })
//
//    object While : ExpressionType({
//        it.trimSpaces().startsWith(WHILE)
//    });
//}
//
//private fun isLiteral(statement: String): Boolean {
//    return arrayOf(
//        ExpressionType.IntLiteral,
//        ExpressionType.BoolLiteral,
//        ExpressionType.StringLiteral
//    ).find { it.matches(statement) } != null
//}
//
//private fun isValue(statement: String): Boolean {
//    return isLiteral(statement) || ExpressionType.Variable.matches(statement)
//}