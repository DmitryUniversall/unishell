package core.lexer

data class Token(
    val type: TokenType,
    val text: String,
    val position: Int,
)
