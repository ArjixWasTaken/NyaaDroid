package com.arjixwastaken.nyaaapi.models

import java.net.URI
import java.util.*

data class TorrentPreview(
    val id: Int,
    val torrentState: TorrentState,
    val category: String,
    val title: String,
    val comments: Int,
    val downloadLink: URI,
    val magnetLink: URI,
    val size: DataSize,
    val date: Date,
    val seeders: Int,
    val leechers: Int,
    val completed: Int
)