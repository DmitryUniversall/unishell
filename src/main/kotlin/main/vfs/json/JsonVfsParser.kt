package main.vfs.json

import core.vfs.VfsDirectory
import core.vfs.VfsFile
import core.vfs.VfsNode
import core.vfs.VirtualFileSystem
import java.util.Base64
import kotlinx.serialization.json.Json

class JsonVfsParser(private val json: Json) {
    fun parse(text: String): VirtualFileSystem {
        val document = json.decodeFromString<VfsDocument>(text)
        require(document.name.isNotBlank()) { "VFS name is empty" }

        return VirtualFileSystem(document.name, readDirectory("", document.children))
    }

    private fun readDirectory(name: String, entries: List<VfsEntryDocument>): VfsDirectory {
        val children = mutableMapOf<String, VfsNode>()

        for (entry in entries) {
            require(entry.name.isNotBlank() && '/' !in entry.name && entry.name != "." && entry.name != "..") {
                "Invalid VFS entry name: ${entry.name}"
            }
            require(entry.name !in children) { "Duplicate VFS entry: ${entry.name}" }

            children[entry.name] = when (entry) {
                is DirectoryDocument -> readDirectory(entry.name, entry.children)
                is FileDocument -> VfsFile(entry.name, readContent(entry))
            }
        }

        return VfsDirectory(name, children)
    }

    private fun readContent(file: FileDocument): ByteArray = when (file.encoding) {
        "text" -> file.content.toByteArray(Charsets.UTF_8)
        "base64" -> Base64.getDecoder().decode(file.content)
        else -> throw IllegalArgumentException("Unknown file encoding: ${file.encoding}")
    }
}
