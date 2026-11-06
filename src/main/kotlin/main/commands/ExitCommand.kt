package main.commands

import core.CommandResult
import core.commands.Command

class ExitCommand : Command {
    override val name: String = "exit"

    override fun execute(arguments: List<String>): CommandResult {
        require(arguments.isEmpty()) { "Usage: exit" }

        return CommandResult(shouldExit = true)
    }
}
