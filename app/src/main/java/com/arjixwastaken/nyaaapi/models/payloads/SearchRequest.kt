package com.arjixwastaken.nyaaapi.models.payloads

import com.arjixwastaken.nyaaapi.models.SearchSort
import com.arjixwastaken.nyaaapi.models.SearchOrdering
import com.arjixwastaken.nyaaapi.models.SearchFilter

data class SearchRequestData(
    val query: String,
    val page: Int = 1,
    val category: String = "0_0",
    val byUser: String? = null,
    val sortBy: SearchSort = SearchSort.DATE,
    val filter: SearchFilter = SearchFilter.NONE,
    val ordering: SearchOrdering = SearchOrdering.DESCENDING,
)
