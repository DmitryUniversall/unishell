package main.configuration

class CommandLineParser {
    fun parse(arguments: Array<String>): LaunchOptions {
        val values = mutableMapOf<String, String>()
        val supportedOptions = setOf("--vfs", "--script", "--config")
        var position = 0

        while (position < arguments.size) {
            val option = arguments[position]

            require(option in supportedOptions) { "Unknown option: $option" }
            require(option !in values) { "Duplicate option: $option" }
            require(position + 1 < arguments.size && !arguments[position + 1].startsWith("--")) { "Missing value for $option" }

            val value = arguments[position + 1]
            require(value.isNotBlank()) { "Empty value for $option" }
            values[option] = value
            position += 2
        }

        return LaunchOptions(
            vfsPath = values["--vfs"],
            scriptPath = values["--script"],
            configPath = values["--config"],
        )
    }
}
