package interpreter.parser

import interpreter.statement.IStatement

typealias Code = List<String>
typealias LineCode = String

object CodeParser {

    fun parse(code: Code): IStatement {
        val inlinedCode = code.inline()
        return BlockParser.parse(inlinedCode)
    }

//    private fun LineCode.parseFor() {
//        val keyEndIndex = indexOf(FOR) + FOR.length
//
//        //TODO indices validation
//        val conditionsStartIndex = indexOf(BRACKET_START, keyEndIndex)
//        val conditionsEndIndex = indexOf(BRACKET_END, conditionsStartIndex)
//
//        val conditionsCode = substring(conditionsStartIndex + 1 until conditionsEndIndex)
//        val conditions = conditionsCode.split(SEMICOLON)
//
//        if (conditions.size != 3) throw ParserException("Invalid conditions count (${conditions.size}) in for expression")
//
//        val assignExpression = conditions[0].parseAssignExpression()
//        val loopCondition = conditions[1].parseBoolExpression()
//        val loopEndCondition = conditions[2].parseBoolExpression()
//
//        val statementStartIndex = indexOf(STATEMENT_START, conditionsEndIndex + 1)
//        val statementEndIndex = indexOfStatementEnd(statementStartIndex)
//            ?: throw ParserException("Missing statement end in for expression")
//
//        return
//    }
//
//    private fun LineCode.parseAssignExpression() {
//
//    }
//
//    private fun LineCode.parseBoolExpression() {
//
//    }
//
//
//    // Assign
//
//    fun parseAssign(expression: String): AssignExpression {
//
//    }
//
//    // Variables
//
//    fun parseVariable(expression: String): VariableExpression {
//        if (!ExpressionType.Variable.matches(expression))
//            throw ParserException("$expression is not a variable")
//
//        return VariableExpression(expression)
//    }
//
//    fun parseCreateVariable(expression: String) {
//        if (!ExpressionType.CreateVariable.matches(expression))
//            throw ParserException("$expression is not a create variable expression")
//
//        val variableExpression =
//
//            return CreateVariableExpression(expression)
//    }
//
//    // Literals
//
//    fun parseInt(expression: String): IntLiteral {
//        if (!ExpressionType.IntLiteral.matches(expression))
//            throw ParserException("$expression is not int")
//
//        return IntLiteral(expression.toInt())
//    }
//
//    fun parseBool(expression: String): BoolLiteral {
//        if (!ExpressionType.BoolLiteral.matches(expression))
//            throw ParserException("$expression is not bool")
//
//        return BoolLiteral(expression == TRUE)
//    }
//
//    fun parseString(expression: String): StringLiteral {
//        if (!ExpressionType.StringLiteral.matches(expression))
//            throw ParserException("$expression is not string")
//
//        return StringLiteral(
//            expression.substring(
//                expression.indexOfFirst { it == QUOTE },
//                expression.indexOfLast { it == QUOTE }
//            )
//        )
//    }
}