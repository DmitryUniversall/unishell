package main.vfs

import core.vfs.VfsDirectory
import core.vfs.VfsFile
import core.vfs.VfsNode
import core.vfs.VfsSession
import core.vfs.VirtualFileSystem

class InMemoryVfsSession(private val fileSystem: VirtualFileSystem) : VfsSession {
    private var currentDirectory: List<VfsNode> = listOf(fileSystem.root)

    override fun resolve(path: String): VfsNode = walk(path).last()

    override fun changeDirectory(path: String) {
        val nodes = walk(path)
        require(nodes.last() is VfsDirectory) { "Not a directory: $path" }
        currentDirectory = nodes
    }

    override fun readFile(path: String): ByteArray {
        val node = resolve(path)
        require(node is VfsFile) { "Is a directory: $path" }
        return node.content
    }

    private fun walk(path: String): List<VfsNode> {
        require(path.isNotEmpty()) { "Path is empty" }
        val nodes = if (path.startsWith('/')) {
            mutableListOf<VfsNode>(fileSystem.root)
        } else {
            currentDirectory.toMutableList()
        }

        for (part in path.split('/')) {
            if (part.isEmpty()) continue

            val directory = nodes.last()
            require(directory is VfsDirectory) { "Not a directory: $path" }

            when (part) {
                "." -> continue
                ".." -> if (nodes.size > 1) nodes.removeAt(nodes.lastIndex)
                else -> nodes.add(requireNotNull(directory.children[part]) { "No such file or directory: $path" })
            }
        }

        if (path.endsWith('/')) require(nodes.last() is VfsDirectory) { "Not a directory: $path" }
        return nodes
    }
}
