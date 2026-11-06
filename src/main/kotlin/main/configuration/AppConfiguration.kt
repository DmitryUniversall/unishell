package main.configuration

import java.nio.file.Path

data class AppConfiguration(
    val vfsPath: Path? = null,
    val scriptPath: Path? = null,
    val configPath: Path? = null,
) {
    val vfsName: String
        get() = vfsPath?.fileName?.toString() ?: "default"
}
