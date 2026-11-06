package main.io

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

class Utf8TextFileReader : TextFileReader {
    override fun read(path: Path): String = Files.readString(path, StandardCharsets.UTF_8)
}
