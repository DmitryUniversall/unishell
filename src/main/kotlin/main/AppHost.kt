package main

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import core.Shell
import main.ui.screens.TerminalScreen

private const val uiScale = 1.6f

class AppHost(
    private val shell: Shell,
    private val vfsName: String,
) {
    fun run() = application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "unishell - VFS: $vfsName",
            state = rememberWindowState(width = 900.dp * uiScale, height = 600.dp * uiScale),
        ) {
            val lines = remember {
                mutableStateListOf(
                    "unishell - VFS: $vfsName",
                )
            }

            var inputText by remember { mutableStateOf("") }

            val density = LocalDensity.current

            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = density.density * uiScale,
                    fontScale = density.fontScale,
                ),
            ) {
                MaterialTheme(colorScheme = darkColorScheme()) {
                    TerminalScreen(
                        lines = lines,
                        input = inputText,
                        onInputChange = { inputText = it },
                        onSubmit = {
                            if (inputText.isNotBlank()) {
                                val commandLine = inputText
                                lines.add("$ $commandLine")
                                inputText = ""

                                val result = shell.execute(commandLine)
                                if (result.output.isNotEmpty()) lines.addAll(result.output.lines())
                                if (result.shouldExit) exitApplication()
                            }
                        },
                    )
                }
            }
        }
    }
}
