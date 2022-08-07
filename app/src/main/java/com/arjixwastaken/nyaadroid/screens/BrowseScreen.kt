package com.arjixwastaken.nyaadroid.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.arjixwastaken.nyaadroid.components.TorrentItem
import com.arjixwastaken.nyaadroid.utils.TextSearchBar
import com.arjixwastaken.nyaaapi.models.TorrentPreview
import com.arjixwastaken.nyaaapi.models.payloads.SearchRequest
import com.arjixwastaken.nyaadroid.NyaaAPI
import kotlinx.coroutines.launch


val BrowseItems by lazy { mutableStateListOf<TorrentPreview>() }

@Composable
fun Browse(Items: SnapshotStateList<TorrentPreview>){
    val listState = rememberLazyListState()
    val coroutine = rememberCoroutineScope()

    var previousValue by remember { mutableStateOf("") }
    var currentPage by remember { mutableStateOf(1) }
    var value by remember { mutableStateOf("") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextSearchBar(
                Modifier.fillMaxWidth(.9f),
                value=value,
                label="Search nyaa",
                onClearClick = { value = "" },
                onFocusChanged = {},
                onValueChanged = { value = it },
                onDoneActionClick = {
                    if (previousValue != value) {
                        previousValue = value
                        currentPage = 1
                        BrowseItems.clear()

                        coroutine.launch { BrowseItems.addAll(NyaaAPI.search(SearchRequest(value))) }
                    }
                }
            )
            // TODO: Add a menu to edit the search parameters (eg category)
        }
        LazyColumn(state = listState) {
            itemsIndexed(Items) { index, item ->
                Column() {
                    TorrentItem(item)
                }
            }
        }

//        val a = derivedStateOf { listState.layoutInfo }.value.visibleItemsInfo.lastOrNull()?.let {
//            if (it.index == BrowseItems.size-1) {
//                currentPage++
//                 coroutine.launch { BrowseItems.addAll(NyaaAPI.search(SearchRequest(value, page=currentPage))) }
//            }
//        }
    }
}
