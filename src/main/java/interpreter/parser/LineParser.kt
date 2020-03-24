package interpreter.parser

import interpreter.expression.IExpression
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
import interpreter.parser.utils.*

object LineParser {

    fun parse(codeLine: String, isDirty: Boolean = false): IExpression? {
        var workingLine =
            if (isDirty) codeLine.trimSpaces()
            else codeLine


        if (workingLine.isEmpty()) return null

        val expressions = mutableListOf<IExpression>()

        while (workingLine.isNotEmpty()) {
            val tokenEndIndex = workingLine.indexOf(SPACE)
                .takeIf { index -> index != -1 }
                ?: workingLine.length

            val token = workingLine.substring(0, tokenEndIndex)

            expressions += parseToken(token)

            workingLine = workingLine.substring(tokenEndIndex, workingLine.length).trimStartSpaces()
        }

        return TreeParser.createTree(expressions)
    }

    private fun parseToken(token: String): IExpression {
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
            else -> {
                when {
                    token.isInt() -> IntLiteral(token.toInt())
                    token.isBool() -> BoolLiteral(token == TRUE)
                    token.isString() -> StringLiteral(token.extractString())
                    token.isVariable() -> VariableExpression(token)
                    else -> throw IllegalStateException("Invalid token $token")
                }
            }
        }
    }
}

private object TreeParser {
    fun createTree(expressions: List<IExpression>): IExpression {
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

    private val IExpression.priority: Int
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
}
