package main.commands

import core.CommandResult
import core.commands.Command
import core.vfs.VfsSession

class UniqCommand(private val vfs: VfsSession) : Command {
    override val name: String = "uniq"

    override fun execute(arguments: List<String>): CommandResult {
        require(arguments.size == 1 && arguments.all { it.isNotEmpty() && !it.startsWith('-') }) {
            "Usage: uniq file"
        }

        val text = vfs.readFile(arguments.single()).toString(Charsets.UTF_8)
        val output = StringBuilder()
        var previousLine: String? = null

        for (line in text.reader().readLines()) {
            if (line != previousLine) output.appendLine(line)
            previousLine = line
        }

        return CommandResult(output.toString())
    }
}
