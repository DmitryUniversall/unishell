@file:JvmName("Unishell")

package main

import core.Shell
import core.vfs.VirtualFileSystem
import java.nio.file.Path
import kotlin.system.exitProcess
import kotlinx.serialization.json.Json
import main.commands.CatCommand
import main.commands.CdCommand
import main.commands.ExitCommand
import main.commands.LsCommand
import main.commands.UniqCommand
import main.configuration.CommandLineParser
import main.configuration.ConfigurationLoader
import main.configuration.IniConfigParser
import main.execution.DefaultCommandExecutor
import main.io.Utf8TextFileReader
import main.resolution.MapCommandResolver
import main.resolution.MapEnvironmentResolver
import main.scripting.FileStartupScriptRunner
import main.vfs.InMemoryVfsSession
import main.vfs.JsonVfsLoader
import main.vfs.VfsLoader
import main.vfs.json.JsonVfsParser

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

    val json = Json { classDiscriminator = "type" }
    val vfsLoader: VfsLoader = JsonVfsLoader(fileReader, JsonVfsParser(json))
    val fileSystem = try {
        configuration.vfsPath?.let(vfsLoader::load) ?: VirtualFileSystem("default")
    } catch (exception: IllegalArgumentException) {
        System.err.println("Error: ${exception.message}")
        exitProcess(1)
    }

    val vfsSession = InMemoryVfsSession(fileSystem)
    val shell = Shell(
        environmentResolver = MapEnvironmentResolver(System.getenv()),
        commandResolver = MapCommandResolver(listOf(
            LsCommand(vfsSession),
            CdCommand(vfsSession),
            CatCommand(vfsSession),
            UniqCommand(vfsSession),
            ExitCommand(),
        )),
        commandExecutor = DefaultCommandExecutor(),
    )

    AppHost(
        shell = shell,
        configuration = configuration,
        fileSystem = fileSystem,
        startupScriptRunner = FileStartupScriptRunner(shell, fileReader),
    ).run()
}
