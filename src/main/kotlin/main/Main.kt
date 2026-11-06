@file:JvmName("Unishell")

package main

import core.Shell
import java.nio.file.Path
import kotlin.system.exitProcess
import main.commands.CdCommand
import main.commands.ExitCommand
import main.commands.LsCommand
import main.configuration.CommandLineParser
import main.configuration.ConfigurationLoader
import main.configuration.IniConfigParser
import main.execution.DefaultCommandExecutor
import main.io.Utf8TextFileReader
import main.resolution.MapCommandResolver
import main.resolution.MapEnvironmentResolver
import main.scripting.FileStartupScriptRunner

fun main(args: Array<String>) {
    val fileReader = Utf8TextFileReader()

    val configurationLoader = ConfigurationLoader(
        commandLineParser = CommandLineParser(),
        iniConfigParser = IniConfigParser(),
        fileReader = fileReader,
        workingDirectory = Path.of("").toAbsolutePath(),
    )

    val configuration = try {
        configurationLoader.load(args)
    } catch (exception: IllegalArgumentException) {
        System.err.println("Error: ${exception.message}")
        System.err.println("Usage: unishell [--vfs path] [--script path] [--config path]")
        exitProcess(1)
    }

    val shell = Shell(
        environmentResolver = MapEnvironmentResolver(System.getenv()),
        commandResolver = MapCommandResolver(listOf(LsCommand(), CdCommand(), ExitCommand())),
        commandExecutor = DefaultCommandExecutor(),
    )

    AppHost(
        shell = shell,
        configuration = configuration,
        startupScriptRunner = FileStartupScriptRunner(shell, fileReader),
    ).run()
}
