package main.commands

import core.CommandResult
import core.commands.Command
import core.vfs.VfsDirectory
import core.vfs.VfsFile
import core.vfs.VfsSession

class LsCommand(private val vfs: VfsSession) : Command {
    override val name: String = "ls"

    override fun execute(arguments: List<String>): CommandResult {
        require(arguments.size <= 1 && arguments.all { it.isNotEmpty() && !it.startsWith('-') }) {
            "Usage: ls [path]"
        }

        val path = arguments.firstOrNull() ?: "."
        val output = when (val node = vfs.resolve(path)) {
            is VfsDirectory -> node.children.keys.filterNot { it.startsWith('.') }.sorted().joinToString("\n")
            is VfsFile -> path
        }
        return CommandResult(output)
    }
}
