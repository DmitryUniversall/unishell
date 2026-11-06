package main.commands

import core.CommandResult
import core.commands.Command
import core.vfs.VfsSession

class CatCommand(private val vfs: VfsSession) : Command {
    override val name: String = "cat"

    override fun execute(arguments: List<String>): CommandResult {
        require(arguments.isNotEmpty() && arguments.all { it.isNotEmpty() && !it.startsWith('-') }) {
            "Usage: cat file..."
        }

        val output = arguments.joinToString("") { path -> vfs.readFile(path).toString(Charsets.UTF_8) }
        return CommandResult(output)
    }
}
