package main.configuration

import org.apache.commons.configuration2.INIConfiguration
import org.apache.commons.configuration2.ex.ConfigurationException

class IniConfigParser {
    fun parse(text: String): LaunchOptions {
        val ini = INIConfiguration()

        try {
            ini.read(text.reader())
        } catch (exception: ConfigurationException) {
            throw IllegalArgumentException(exception.message, exception)
        }

        require(ini.sections.all { it == null || it == "shell" }) { "Only the [shell] section is supported." }
        for (key in ini.keys) require(key in setOf("vfs", "script", "shell.vfs", "shell.script")) { "Unknown INI key: $key" }

        return LaunchOptions(
            vfsPath = readPath(ini, "shell.vfs") ?: readPath(ini, "vfs"),
            scriptPath = readPath(ini, "shell.script") ?: readPath(ini, "script"),
        )
    }

    private fun readPath(ini: INIConfiguration, key: String): String? {
        val value = ini.getProperty(key) ?: return null
        require(value is String && value.isNotBlank()) { "Expected a single non-empty value for '$key'." }
        return value
    }
}
