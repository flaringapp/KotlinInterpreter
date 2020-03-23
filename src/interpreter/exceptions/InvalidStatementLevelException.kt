package interpreter.exceptions

import interpreter.parser.STATEMENT_END

class InvalidStatementLevelException (
    val line: String,
    val index: Int
): ParserException("Invalid statement closure (\'$STATEMENT_END\') at index $index in line $line")