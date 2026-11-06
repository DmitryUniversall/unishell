package main.scripting

import core.CommandResult
import java.nio.file.Path

interface StartupScriptRunner {
    fun run(path: Path, writeLine: (String) -> Unit): CommandResult
}
