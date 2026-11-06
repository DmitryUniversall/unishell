package core

import core.execution.CommandExecutor
import core.lexer.Lexer
import core.parser.CommandParser
import core.resolution.CommandResolver
import core.resolution.EnvironmentResolver

class Shell(
    private val environmentResolver: EnvironmentResolver,
    private val commandResolver: CommandResolver,
    private val commandExecutor: CommandExecutor,
) {
    fun execute(line: String): CommandResult {
        try {
            val tokens = Lexer(line).tokenize()
            val parsedCommand = CommandParser(tokens).parse() ?: return CommandResult()

            val invocation = environmentResolver.resolve(parsedCommand)
            val command = commandResolver.resolve(invocation.name)

            return commandExecutor.execute(command, invocation.arguments)
        } catch (exception: IllegalArgumentException) {
            val message = exception.message ?: "Failed to process command."
            return CommandResult(output = "Error: $message", isError = true)
        }
    }
}
