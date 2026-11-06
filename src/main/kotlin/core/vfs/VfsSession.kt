package core.vfs

interface VfsSession {
    val currentPath: String

    fun resolve(path: String): VfsNode

    fun changeDirectory(path: String)

    fun createDirectory(path: String)

    fun readFile(path: String): ByteArray
}
