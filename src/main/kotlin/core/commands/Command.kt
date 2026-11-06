package core.commands

import core.CommandResult

interface Command {
    val name: String

    fun execute(arguments: List<String>): CommandResult
}
