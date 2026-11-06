package core.parser

sealed interface WordPart {
    data class Literal(val value: String) : WordPart
    data class Variable(val name: String) : WordPart
}
