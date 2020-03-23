package interpreter.exceptions

class UnsupportedStatementTypeException(
    val statement: String
) : ParserException("Unsupported statement type : $statement")