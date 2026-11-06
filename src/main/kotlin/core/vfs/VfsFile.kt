package core.vfs

class VfsFile(
    override val name: String,
    val content: ByteArray,
) : VfsNode
