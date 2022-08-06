package com.arjixwastaken.nyaaapi

class Sukebei: Nyaa() {
    override val HOST = "sukebei.nyaa.si"
    override val MainCategories = listOf("Art", "Real Life")
    override val SubCategories = listOf(
        listOf("Anime", "Doujinshi", "Games", "Manga", "Pictures"),
        listOf("PhotoBooks and Pictures", "Videos")
    )
}