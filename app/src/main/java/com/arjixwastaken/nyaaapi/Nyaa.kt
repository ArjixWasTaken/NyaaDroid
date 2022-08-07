package com.arjixwastaken.nyaaapi

import android.annotation.SuppressLint
import com.arjixwastaken.nyaaapi.models.AccountInfo
import com.arjixwastaken.nyaaapi.models.DataSize
import com.arjixwastaken.nyaaapi.models.TorrentPreview
import com.arjixwastaken.nyaaapi.models.TorrentState
import com.arjixwastaken.nyaaapi.models.payloads.SearchRequest
import com.arjixwastaken.nyaaapi.utils.getAfterDt
import java.lang.Exception
import com.lagradost.nicehttp.Requests
import java.net.URI
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.streams.toList

open class Nyaa {
    private var sessionCookie: Map<String, String>? = null
    private val client = Requests()

    open val HOST = "nyaa.si"
    open val MainCategories = listOf("Anime", "Audio", "Literature", "Live Action", "Pictures")
    open val SubCategories = listOf(
        listOf("Music Video", "English Translated", "Non-English Translated", "Raw"),
        listOf("Lossless", "Lossy"),
        listOf("English Translated", "Non-English Translated", "Raw"),
        listOf("English Translated", "Idol/Promotional Video", "Non-English Translated", "Raw"),
        listOf("Graphics", "Photos"),
        listOf("Applications", "Games"),
    )

    fun categoryIdToString(category: String): String {
        if (category.split("_").size != 2) throw Exception("Category \"$category\" is not a valid category id!")
        val (mainCategory, subCategory) = category.split("_").map { it.toInt() }
        if (mainCategory == 0) return "All Categories"

        val result = MainCategories[mainCategory-1]
        if (subCategory == 0) return result

        return "$result ${SubCategories[mainCategory-1][subCategory-1]}"
    }

    fun categoryStringToId(category: String): String {
        if (category == "All Categories") return "0_0"

        val parts = category.split(" - ")
        val result = MainCategories.indexOf(parts[0])
        if (parts.size == 1) return "${result+1}_0"
        return "${result+1}_${SubCategories[result].indexOf(parts[1])+1}"
    }

    fun setSessionCookie(sessionId: String) {
        sessionCookie = mapOf("session" to sessionId)
    }

    suspend fun search(payload: SearchRequest):  List<TorrentPreview> {
        val idRegex = Regex("""/view/(\d+)""")
        val catRegex = Regex("""(\d_\d)\.png""")

        val queryParams = mapOf<String, String>(
            "f" to payload.filter.ordinal.toString(),
            "c" to payload.category,
            "q" to URLEncoder.encode(payload.query, "UTF-8"),
            "s" to payload.sortBy.name.lowercase().let { if (it == "date") "id" else it },
            "o" to payload.ordering.name.lowercase().substringBefore("c") + "c",
            "p" to payload.page.toString(),
        )
            .entries.stream()
            .map { "${it.key}=${it.value}" }
            .toList().joinToString("&")

        val url = "https://$HOST/${payload.byUser ?: ""}?$queryParams"
        val doc = client.get(url).document

        return doc.select("table > tbody > tr").map {
            val TDs = it.select("td")
            val id = idRegex.find(TDs[1].selectFirst("a:last-child")!!.attr("href"))?.groupValues?.get(1)?.toIntOrNull() ?: 0
            TorrentPreview(
                id,
                when (it.className()) {
                    "danger" -> TorrentState.REMAKE
                    "success" -> TorrentState.TRUSTED
                    else -> TorrentState.NORMAL
                },
                catRegex.find(TDs[0].selectFirst("img")!!.attr("src"))?.groupValues?.get(1) ?: "N/A",
                TDs[1].selectFirst("a:last-child")!!.text(),
                TDs[1].selectFirst(".comments")?.text()?.toIntOrNull() ?: 0,
                URI("https://$HOST/download/$id.torrent"),
                URI(TDs[2].selectFirst("a:last-child")!!.attr("href")),
                DataSize.fromString(TDs[3].text()),
                Date(((TDs[4].attr("data-timestamp").toIntOrNull() ?: 0) * 1000).toLong()),
                TDs[5].text().toInt(),
                TDs[6].text().toInt(),
                TDs[7].text().toInt(),
            )
        }
    }

    @SuppressLint("SimpleDateFormat")
    suspend fun getAccountInfo(): AccountInfo {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        if (sessionCookie == null) throw Exception("You must be logged-in in order to use account related methods!")
        val doc = client.get("https://$HOST/profile", cookies = sessionCookie!!).document

        return AccountInfo(
            doc.selectFirst("h2 > strong.text-default")?.text() ?: "Anonymous",
            getAfterDt(doc, "User ID:").text().toInt(),
            dateFormat.parse(getAfterDt(doc, "User Created on:").text()) ?: Date(),
            doc.selectFirst("img.avatar")!!.attr("src"),
            getAfterDt(doc, "User Class:").text(),
            doc.selectFirst("div#email-change")?.selectFirst("label[for=current_email] + div")?.text() ?: ""
        )
    }
}