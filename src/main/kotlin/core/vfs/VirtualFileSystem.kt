package core.vfs

class VirtualFileSystem(
    val name: String,
    val root: VfsDirectory = VfsDirectory(""),
)
