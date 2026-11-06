package main.configuration

import java.nio.file.Path

data class AppConfiguration(
    val vfsPath: Path? = null,
    val scriptPath: Path? = null,
    val configPath: Path? = null,
)
