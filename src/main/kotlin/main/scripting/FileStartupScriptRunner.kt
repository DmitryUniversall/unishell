package main.scripting

import core.CommandResult
import core.Shell
import main.io.TextFileReader
import java.io.IOException
import java.nio.file.Path

class FileStartupScriptRunner(
    private val shell: Shell,
    private val fileReader: TextFileReader,
) : StartupScriptRunner {
    override fun run(path: Path, writeLine: (String) -> Unit): CommandResult {
        val script = try {
            fileReader.read(path)
        } catch (exception: IOException) {
            return reportReadError(path, exception, writeLine)
        }

        for ((index, line) in script.lineSequence().withIndex()) {
            if (line.isBlank()) continue

            writeLine("$ $line")
            val result = shell.execute(line)
            if (result.output.isNotEmpty()) result.output.lines().forEach(writeLine)

            if (result.isError) {
                writeLine("Startup script '$path' stopped at line ${index + 1}.")
                return result
            }

            if (result.shouldExit) return result
        }

        return CommandResult()
    }

    private fun reportReadError(path: Path, exception: IOException, writeLine: (String) -> Unit): CommandResult {
        val message = "Error: Cannot read startup script '$path': ${exception.message}"
        writeLine(message)
        return CommandResult(output = message, isError = true)
    }
}
