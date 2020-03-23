package interpreter.parser

val spaces = charArrayOf(' ', '\t')

fun String.trimSpaces() = trim(*spaces)
fun String.trimStartSpaces() = trimStart(*spaces)
fun String.trimEndSpaces() = trimStart(*spaces)

fun String.isDigitsOnly() = find { !it.isDigit() } == null
fun String.isLettersOnly() = find { !it.isLetter() } == null

fun String.startsWithDigit() = isEmpty() || first().isDigit()
fun String.endsWithDigit() = isEmpty() || last().isDigit()

fun String.isDigitsOrLetters() = find { !it.isDigit() && !it.isLetter() } == null

fun String.isInt() = isDigitsOnly()
fun String.isBool() = this == TRUE || this == FALSE
fun String.isString() = length >= 2 && startsWith(QUOTE) && endsWith(QUOTE)
fun String.isVariable() = !startsWithDigit() && substring(1, length).isDigitsOrLetters()

fun String.extractString() = substring(indexOfFirst { it == QUOTE } + 1, indexOfLast { it == QUOTE })

private fun Code.toLine() = reduce { acc, line ->
    acc + line.trimSpaces() + NEW_LINE
}

fun LineCode.indexOfStatementEnd(startIndex: Int): Int? {
    var currentStatementLevel = 0

    forEachIndexed { index, char ->
        when (char) {
            STATEMENT_START -> currentStatementLevel++
            STATEMENT_END -> {
                currentStatementLevel--
                if (currentStatementLevel == -1) return index
            }
        }
    }

    return null
}