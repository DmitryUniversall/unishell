package main.commands

import core.CommandResult
import core.commands.Command
import core.vfs.VfsSession

class MkdirCommand(private val vfs: VfsSession) : Command {
    override val name: String = "mkdir"

    override fun execute(arguments: List<String>): CommandResult {
        require(arguments.size == 1 && arguments.all { it.isNotEmpty() && !it.startsWith('-') }) {
            "Usage: mkdir path"
        }

        vfs.createDirectory(arguments.single())
        return CommandResult()
    }
}
