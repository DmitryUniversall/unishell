package core.execution

import core.CommandResult
import core.commands.Command

interface CommandExecutor {
    fun execute(command: Command, arguments: List<String>): CommandResult
}
