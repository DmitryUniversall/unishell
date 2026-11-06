package core

data class CommandResult(
    val output: String = "",
    val isError: Boolean = false,
    val shouldExit: Boolean = false,
)
