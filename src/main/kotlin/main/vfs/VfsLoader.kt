package main.vfs

import core.vfs.VirtualFileSystem
import java.nio.file.Path

interface VfsLoader {
    fun load(path: Path): VirtualFileSystem
}
