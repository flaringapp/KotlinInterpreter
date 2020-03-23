package interpreter.parser

import interpreter.expression.IExpression
import interpreter.expression.ITypedValueExpression
import interpreter.expression.nonTerminal.BinaryExpression
import interpreter.expression.nonTerminal.UnaryExpression
import interpreter.expression.nonTerminal.arithmetic.AddExpression
import interpreter.expression.nonTerminal.arithmetic.DivideExpression
import interpreter.expression.nonTerminal.arithmetic.SubtractExpression
import interpreter.expression.nonTerminal.arithmetic.TimesExpression
import interpreter.expression.nonTerminal.comparing.*
import interpreter.expression.nonTerminal.logical.LogicalAndExpression
import interpreter.expression.nonTerminal.logical.LogicalNotExpression
import interpreter.expression.nonTerminal.logical.LogicalOrExpression
import interpreter.expression.nonTerminal.util.PrintExpression
import interpreter.expression.nonTerminal.variable.AssignExpression
import interpreter.expression.nonTerminal.variable.CreateVariableExpression
import interpreter.expression.terminal.*
import interpreter.statement.ComplexStatement
import interpreter.statement.IStatement

typealias Code = List<String>
typealias LineCode = String

object CodeParser {

    fun parse(code: Code): IStatement {
        return parseAsTrees(code.inline())
    }

    private fun parseAsTrees(code: Code): IStatement {
        val trees = mutableListOf<IExpression>()

        code.map {
            var line = it.trimSpaces()

            if (line.isEmpty()) return ComplexStatement.empty()

            val expressions = mutableListOf<IExpression>()

            while (line.isNotEmpty()) {
                val tokenEndIndex = line.indexOf(SPACE)
                    .takeIf { index -> index != -1 }
                    ?: line.length

                val token = line.substring(0, tokenEndIndex)

                expressions += parseSimpleToken(token)

                line = line.substring(tokenEndIndex, line.length).trimStartSpaces()
            }

            trees += createTree(expressions)
        }

        return ComplexStatement(trees)
    }

    private fun parseSimpleToken(token: String): IExpression {
        return when (token) {
            VAR_DECLARATION -> CreateVariableExpression()
            ASSIGN -> AssignExpression()
            PLUS -> AddExpression()
            MINUS -> SubtractExpression()
            STAR -> TimesExpression()
            SLASH -> DivideExpression()
            MORE -> MoreExpression()
            LESS -> LessExpression()
            EQUALS -> EqualsExpression()
            NOT_EQUALS -> NotEqualsExpression()
            MORE_EQUALS -> MoreEqualsExpression()
            LESS_EQUALS -> LessEqualsExpression()
            AND -> LogicalAndExpression()
            OR -> LogicalOrExpression()
            NOT -> LogicalNotExpression()
            PRINT -> PrintExpression()
            else -> parseValueToken(token)
        }
    }

    private fun parseValueToken(token: String): ITypedValueExpression<*> {
        return when {
            token.isInt() -> IntLiteral(token.toInt())
            token.isBool() -> BoolLiteral(token == TRUE)
            token.isString() -> StringLiteral(token.extractString())
            token.isVariable() -> VariableExpression(token)
            else -> throw IllegalStateException("Invalid token $token")
        }
    }

    private fun createTree(expressions: List<IExpression>): IExpression {
        if (expressions.isEmpty()) throw IllegalStateException("No expressions for statement!")

        var treeRoot: IExpression? = null

        expressions.forEach {
            treeRoot = addToTree(treeRoot, it)
        }

        return treeRoot!!
    }

    private fun addToTree(root: IExpression?, expression: IExpression): IExpression {
        if (root == null) return expression

        return if (expression.priority < root.priority) {
            typedAddToTree(root, expression)
            root
        } else {
            typedAddToTree(expression, root)
            expression
        }
    }

    private fun typedAddToTree(root: IExpression, expression: IExpression): IExpression {
        when (root) {
            is BinaryExpression -> {
                when {
                    !root.hasLeftExpression() -> root.leftExpression = addToTree(null, expression)
                    !root.hasRightExpression() -> root.rightExpression = addToTree(null, expression)
                    else -> root.rightExpression = addToTree(root.rightExpression, expression)
                }
            }
            is UnaryExpression -> {
                if (!root.hasExpression()) root.expression = addToTree(null, expression)
                else root.expression = addToTree(root.expression, expression)
            }
            else -> {
                if (expression is ITerminalExpression) throw IllegalStateException("Duplicate $expression?")
                return addToTree(expression, root)
            }
        }

        return root
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

val IExpression.priority: Int
    get() = when (this) {
        is PrintExpression -> 200
        is AssignExpression -> 100
        is LogicalAndExpression, is LogicalOrExpression -> 90
        is LogicalNotExpression -> 85
        is EqualsExpression, is NotEqualsExpression, is LessExpression, is MoreExpression,
        is LessEqualsExpression, is MoreEqualsExpression -> 80
        is AddExpression, is SubtractExpression -> 70
        is TimesExpression, is DivideExpression -> 50
        is CreateVariableExpression -> 2
        is VariableExpression -> 1
        is ITerminalExpression -> 0
        else -> throw IllegalStateException("Priority of ${this::class.simpleName} is not supported")
    }