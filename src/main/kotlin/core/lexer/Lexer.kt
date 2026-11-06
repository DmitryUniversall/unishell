package core.lexer

class Lexer(private val source: String) {
    private val variableStartPattern = Regex("[a-zA-Z_]")
    private val variablePartPattern = Regex("[a-zA-Z0-9_]")

    private var position = 0

    fun tokenize(): List<Token> {
        val tokens = mutableListOf<Token>()

        while (hasNext()) {
            val char = source[position]

            val token = when {
                char.isWhitespace() -> readWhitespace()
                char == '\'' -> readQuote(TokenType.SINGLE_QUOTE)
                char == '"' -> readQuote(TokenType.DOUBLE_QUOTE)
                isVariableStart() -> readVariable()
                else -> readText()
            }

            tokens.add(token)
        }

        return tokens
    }

    private fun readWhitespace(): Token {
        val start = position

        while (hasNext() && source[position].isWhitespace()) position++

        return Token(TokenType.WHITESPACE, source.substring(start, position), start)
    }

    private fun readQuote(type: TokenType): Token {
        val token = Token(type, source[position].toString(), position)

        position++

        return token
    }

    private fun readVariable(): Token {
        val start = position
        position++ // Skip $

        while (hasNext() && isVariablePart(source[position])) position++

        return Token(TokenType.VARIABLE, source.substring(start, position), start)
    }

    private fun readText(): Token {
        val start = position

        while (hasNext()) {
            val char = source[position]
            if (char.isWhitespace() || char == '\'' || char == '"' || isVariableStart()) break
            position++
        }

        return Token(TokenType.TEXT, source.substring(start, position), start)
    }

    private fun isVariableStart(): Boolean =
        source[position] == '$' &&
            position + 1 < source.length &&
            isVariableStart(source[position + 1])

    private fun hasNext(): Boolean = position < source.length

    private fun isVariableStart(char: Char): Boolean = variableStartPattern.matches(char.toString())

    private fun isVariablePart(char: Char): Boolean = variablePartPattern.matches(char.toString())
}
