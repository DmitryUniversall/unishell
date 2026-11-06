package main.commands

import core.CommandResult
import core.commands.Command
import core.vfs.VfsSession

class CdCommand(private val vfs: VfsSession) : Command {
    override val name: String = "cd"

    override fun execute(arguments: List<String>): CommandResult {
        require(arguments.size == 1 && arguments.all { it.isNotEmpty() && !it.startsWith('-') }) {
            "Usage: cd path"
        }

        vfs.changeDirectory(arguments.single())
        return CommandResult()
    }
}
