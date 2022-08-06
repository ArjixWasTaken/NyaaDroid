package com.arjixwastaken.nyaaapi.utils

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.lang.Exception

fun getAfterDt(page: Document, textOfDit: String): Element {
    return page.select("dt").stream()
        .filter { e -> e.text().equals(textOfDit) }.findAny()
        .orElseThrow { Exception("Cannot find `<dt>$textOfDit</dt>`") }
        .nextElementSibling()!!
}
