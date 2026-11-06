package main.vfs.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VfsDocument(
    val name: String,
    val children: List<VfsEntryDocument> = emptyList(),
)

@Serializable
sealed interface VfsEntryDocument {
    val name: String
}

@Serializable
@SerialName("directory")
data class DirectoryDocument(
    override val name: String,
    val children: List<VfsEntryDocument> = emptyList(),
) : VfsEntryDocument

@Serializable
@SerialName("file")
data class FileDocument(
    override val name: String,
    val content: String,
    val encoding: String = "text",
) : VfsEntryDocument
