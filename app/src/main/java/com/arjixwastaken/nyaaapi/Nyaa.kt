package com.arjixwastaken.nyaaapi

import android.annotation.SuppressLint
import com.arjixwastaken.nyaaapi.models.AccountInfo
import com.arjixwastaken.nyaaapi.utils.getAfterDt
import java.lang.Exception
import com.lagradost.nicehttp.Requests
import java.text.SimpleDateFormat
import java.util.*

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