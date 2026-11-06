package main.resolution

import core.commands.Command
import core.resolution.CommandResolver

class MapCommandResolver(commands: Iterable<Command>) : CommandResolver {
    private val commandsByName: Map<String, Command> = commands.associateBy { it.name }

    override fun resolve(name: String): Command = requireNotNull(commandsByName[name]) { "Unknown command: $name" }
}
