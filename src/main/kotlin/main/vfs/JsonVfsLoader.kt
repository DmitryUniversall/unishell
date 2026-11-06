package main.vfs

import core.vfs.VirtualFileSystem
import java.io.IOException
import java.nio.file.Path
import main.io.TextFileReader
import main.vfs.json.JsonVfsParser

class JsonVfsLoader(
    private val fileReader: TextFileReader,
    private val parser: JsonVfsParser,
) : VfsLoader {
    override fun load(path: Path): VirtualFileSystem {
        val text = try {
            fileReader.read(path)
        } catch (exception: IOException) {
            throw IllegalArgumentException("Cannot read VFS '$path': ${exception.message}", exception)
        }

        return try {
            parser.parse(text)
        } catch (exception: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid VFS '$path': ${exception.message}", exception)
        }
    }
}
