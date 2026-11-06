package main.configuration

import main.io.TextFileReader
import java.io.IOException
import java.nio.file.Path

class ConfigurationLoader(
    private val commandLineParser: CommandLineParser,
    private val iniConfigParser: IniConfigParser,
    private val fileReader: TextFileReader,
    private val workingDirectory: Path,
) {
    fun load(arguments: Array<String>): AppConfiguration {
        val options = commandLineParser.parse(arguments)
        val configPath = resolvePath(options.configPath, workingDirectory)
        val fileOptions = if (configPath == null) LaunchOptions() else readConfiguration(configPath)
        val configDirectory = configPath?.parent ?: workingDirectory

        return AppConfiguration(
            vfsPath = resolvePath(options.vfsPath, workingDirectory)
                ?: resolvePath(fileOptions.vfsPath, configDirectory),
            scriptPath = resolvePath(options.scriptPath, workingDirectory)
                ?: resolvePath(fileOptions.scriptPath, configDirectory),
            configPath = configPath,
        )
    }

    private fun readConfiguration(path: Path): LaunchOptions {
        val text = try {
            fileReader.read(path)
        } catch (exception: IOException) {
            throw IllegalArgumentException("Cannot read configuration '$path': ${exception.message}", exception)
        }

        return try {
            iniConfigParser.parse(text)
        } catch (exception: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid configuration '$path': ${exception.message}", exception)
        }
    }

    private fun resolvePath(value: String?, baseDirectory: Path): Path? =
        value?.let { baseDirectory.resolve(it).toAbsolutePath().normalize() }
}
