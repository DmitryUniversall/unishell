package main.resolution

import core.parser.ParsedCommand
import core.parser.Word
import core.parser.WordPart
import core.resolution.CommandInvocation
import core.resolution.EnvironmentResolver

class MapEnvironmentResolver(
    private val environment: Map<String, String>,
) : EnvironmentResolver {
    override fun resolve(command: ParsedCommand): CommandInvocation = CommandInvocation(
        name = resolveWord(command.name),
        arguments = command.arguments.map { resolveWord(it) },
    )

    private fun resolveWord(word: Word): String = word.parts.joinToString(separator = "") { part ->
        when (part) {
            is WordPart.Literal -> part.value
            is WordPart.Variable -> environment[part.name].orEmpty()
        }
    }
}
