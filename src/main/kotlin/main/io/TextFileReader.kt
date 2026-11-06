package main.io

import java.nio.file.Path

interface TextFileReader {
    fun read(path: Path): String
}
