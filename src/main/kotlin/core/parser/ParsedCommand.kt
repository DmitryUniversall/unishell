package core.parser

data class ParsedCommand(
    val name: Word,
    val arguments: List<Word>,
)
