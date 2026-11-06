@file:JvmName("Unishell")

package main

import core.Shell
import main.commands.CdCommand
import main.commands.ExitCommand
import main.commands.LsCommand
import main.execution.DefaultCommandExecutor
import main.resolution.MapCommandResolver
import main.resolution.MapEnvironmentResolver

fun main() {
    val shell = Shell(
        environmentResolver = MapEnvironmentResolver(System.getenv()),
        commandResolver = MapCommandResolver(listOf(LsCommand(), CdCommand(), ExitCommand())),
        commandExecutor = DefaultCommandExecutor(),
    )

    AppHost(shell = shell, vfsName = "default").run()
}
