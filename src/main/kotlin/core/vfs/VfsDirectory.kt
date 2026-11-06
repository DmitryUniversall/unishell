package core.vfs

class VfsDirectory(
    override val name: String,
    val children: MutableMap<String, VfsNode> = mutableMapOf(),
) : VfsNode
