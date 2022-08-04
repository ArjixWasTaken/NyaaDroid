package com.arjixwastaken.nyaadroid.api

import com.arjixwastaken.nyaadroid.app


data class TorrentResult(
    val title: String,
    val category: String,
    val comments: Int = 0,
    val torrentLink: String,
    val magnetLink: String,
    val size: String,
    val date: Int = 0,
    val seeders: Int = 0,
    val leechers: Int = 0,
    val completed: Int = 0,
)


class Torrents {
    companion object {
        val Instance: Torrents
            get() {
                if (instance == null) instance = Torrents()
                return instance!!
            }
        private var instance: Torrents? = null;
    }
    init { instance = this }


    suspend fun FetchTorrentsFromPage(page: String): List<TorrentResult> {
        val res = app.get(page)
        if (!res.isSuccessful) return emptyList()

        val doc = res.document
        return doc.select("table > tbody > tr").map {
            TorrentResult(
                it.selectFirst("td:nth-child(2) a:last-child")?.text().toString(),
                "",
                it.selectFirst("a.comments")?.text()?.toIntOrNull() ?: 0,
                "",
                "",
                it.selectFirst("td:nth-child(4)")?.text().toString(),
                it.selectFirst("td[data-timestamp]")?.dataset()?.get("timestamp")?.toIntOrNull() ?: 0,
                it.selectFirst("td:nth-child(6)")?.text()?.toIntOrNull() ?: 0,
                it.selectFirst("td:nth-child(7)")?.text()?.toIntOrNull() ?: 0,
                it.selectFirst("td:last-child")?.text()?.toIntOrNull() ?: 0
            )
        }
    }
}