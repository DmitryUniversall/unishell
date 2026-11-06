package main.execution

import core.CommandResult
import core.commands.Command
import core.execution.CommandExecutor

class DefaultCommandExecutor : CommandExecutor {
    override fun execute(command: Command, arguments: List<String>): CommandResult =
        try {
            command.execute(arguments)
        } catch (exception: IllegalArgumentException) {
            val message = exception.message ?: "Failed to execute command: ${command.name}"
            CommandResult(output = "Error: $message", isError = true)
        }
}
