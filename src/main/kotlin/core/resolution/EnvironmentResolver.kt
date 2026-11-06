package core.resolution

import core.parser.ParsedCommand

interface EnvironmentResolver {
    fun resolve(command: ParsedCommand): CommandInvocation
}
