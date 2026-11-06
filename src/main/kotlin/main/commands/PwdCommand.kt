package main.commands

import core.CommandResult
import core.commands.Command
import core.vfs.VfsSession

class PwdCommand(private val vfs: VfsSession) : Command {
    override val name: String = "pwd"

    override fun execute(arguments: List<String>): CommandResult {
        require(arguments.isEmpty()) { "Usage: pwd" }
        return CommandResult(vfs.currentPath)
    }
}
