package core.vfs

interface VfsSession {
    fun resolve(path: String): VfsNode

    fun changeDirectory(path: String)

    fun readFile(path: String): ByteArray
}
