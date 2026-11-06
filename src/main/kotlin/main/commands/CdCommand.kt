package main.commands

import core.CommandResult
import core.commands.Command

class CdCommand : Command {
    override val name: String = "cd"

    override fun execute(arguments: List<String>): CommandResult {
        require(arguments.size == 1 && arguments.all { it.isNotEmpty() && !it.startsWith('-') }) {
            "Usage: cd path/to/file, flags are not yet supported"
        }

        val formattedArguments = arguments.joinToString(", ", prefix = "[", postfix = "]") { "\"$it\"" }
        return CommandResult("$name: arguments = $formattedArguments")
    }
}
