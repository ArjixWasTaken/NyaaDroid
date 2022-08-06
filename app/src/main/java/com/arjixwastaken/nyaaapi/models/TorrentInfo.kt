package com.arjixwastaken.nyaaapi.models

import java.util.*


interface FileNode { val name: String }
class File(override val name: String, val size: DataSize) : FileNode
class Folder(override val name: String, val children: List<FileNode>) : FileNode

data class Comment(
    val commentId: Int,
    val username: String,
    val isTrusted: Boolean,
    val avatar: String,
    val date: Date,
    val content: String
)

data class TorrentInfo(
    val title: String,
    val description: String,
    val category: String,
    val size: DataSize,
    val date: Date,
    val uploader: String? = null,
    val torrentState: TorrentState,
    val seeders: Int,
    val leechers: Int,
    val completed: Int,
    val information: String,
    val hash: String,
    val downloadLink: String,
    val magnetLink: String,
    val file: FileNode,
    val comments: List<Comment>
)