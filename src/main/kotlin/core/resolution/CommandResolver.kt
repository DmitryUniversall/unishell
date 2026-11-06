package core.resolution

import core.commands.Command

interface CommandResolver {
    fun resolve(name: String): Command
}
