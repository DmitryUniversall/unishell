package main.commands

import core.CommandResult
import core.commands.Command

class LsCommand : Command {
    override val name: String = "ls"

    override fun execute(arguments: List<String>): CommandResult {
        require(arguments.size <= 1 && arguments.all { it.isNotEmpty() && !it.startsWith('-') }) {
            "Usage: ls [path], flags are not yet supported"
        }

        val formattedArguments = arguments.joinToString(", ", prefix = "[", postfix = "]") { "\"$it\"" }
        return CommandResult("$name: arguments = $formattedArguments")
    }
}
