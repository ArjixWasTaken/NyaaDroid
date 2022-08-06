package com.arjixwastaken.nyaaapi.models.payloads

data class UploadTorrentRequest(
    val name: String,
    val category: String,
    val torrent: ByteArray,
    val information: String? = null,
    val description: String? = null,
    val isAnonymous: Boolean = false,
    val isHidden: Boolean = false,
    val isRemake: Boolean = false,
    val isComplete: Boolean = false,
)